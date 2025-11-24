/**
 * 
 */
package org.tedros.ai;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.AppCatalog;
import org.tedros.ai.function.model.CallView;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.ModuleInfo;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.function.model.ViewInfo;
import org.tedros.ai.function.model.ViewPath;
import org.tedros.ai.model.CreateBinaryFile;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.api.presenter.ITDynaPresenter;
import org.tedros.api.presenter.behavior.ITBehavior;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TReflections;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
import org.tedros.core.domain.TSystemPropertie;
import org.tedros.core.service.remote.TEjbServiceLocator;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TLoggerUtil;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;

/**
 * @author Davis Gordon
 *
 */
public class TFunctionHelper {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(TFunctionHelper.class);

	/**
	 * 
	 */
	private TFunctionHelper() {
	}
	
	@SuppressWarnings("rawtypes")
	public static TFunction[] getAppsFunction(){
		TFunction[] arr = new TFunction[] {};
		Set<Class<? extends TFunction>> clss = TReflections.getInstance().getSubTypesOf(TFunction.class);
		if(clss!=null && !clss.isEmpty()) {
			for(Class<? extends TFunction> c : clss){
				try {
					arr = ArrayUtils.add(arr, c.getDeclaredConstructor().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
						InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return arr;
	}
	
	public static TFunction<CreateBinaryFile> getCreateFileFunction() {
        return new TFunction<>("create_file", """
            Creates any file on the server (PDF, DOCX, XLSX, PNG, ZIP, CSV, etc.).
            Use when user asks to:
            • "Save as PDF"
            • "Export report"
            • "Generate Excel"
            • "Download evidence as file"
            • "Create a document with this analysis"
            Input:
              • name (string) – file name without extension
              • extension (string) – e.g. pdf, docx, xlsx, png, zip
              • base64Content (string) – full file encoded in Base64
              • subfolder (optional) – e.g. "2025/04" or "issue-12345"
            Output: Full file path on server (use !{path} to show in chat)
            """,
            CreateBinaryFile.class,
            request -> {
                try {
                    String dirPath = TedrosFolder.EXPORT_FOLDER.getFullPath();
                    if (request.getSubfolder() != null && !request.getSubfolder().trim().isEmpty()) {
                        dirPath += File.separator + request.getSubfolder().replace("/", File.separator);
                    }

                    Path dir = Path.of(dirPath);
                    Files.createDirectories(dir);

                    String fileName = request.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
                    String fullName = fileName + "." + request.getExtension().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    File file = dir.resolve(fullName).toFile();

                    byte[] data = Base64.getMimeDecoder().decode(request.getBase64Content());
                    FileUtils.writeByteArrayToFile(file, data);

                    String fullPath = file.getAbsolutePath();
                    LOGGER.info("File created successfully: {}", fullPath);

                    return new ToolCallResult("create_file",
                        "File created successfully!\nPath: `!" + fullPath.replace("\\", "\\\\") + "`");

                } catch (Exception e) {
                    LOGGER.error("Failed to create file {}.{}: {}", 
                        request.getName(), request.getExtension(), e.getMessage(), e);
                    return new Response("Error creating file: " + e.getMessage());
                }
            });
    }
	
	/*public static TFunction<CreateFile> getCreateSimpleFileFunction() {
		return new TFunction<CreateFile>("create_simple_file", "Creates simple file with text content.", 
				CreateFile.class, 
				v->{
					
					String dir = TedrosFolder.EXPORT_FOLDER.getFullPath();
					String path = dir+v.getName()+"."+v.getExtension();
					File f = new File(path);
					try(OutputStream out = new FileOutputStream(f)) {
						IOUtils.write(v.getContent(), out, Charset.forName("UTF-8"));
						return new Response("File created! return file path like this: '!{"+path+"}'");
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						return new Response("Error: "+e.getMessage());
					}
				});
	}*/
	
	public static TFunction<Empty> getPreferencesFunction() {
		return new TFunction<Empty>("get_system_preferences", "Returns the system preferences for chat server, smtp server, "
				+ "view history page, openai, teros status, reports, notify, currency/date format and others", 
				Empty.class, 
				v->{
					TEjbServiceLocator loc = TEjbServiceLocator.getInstance();
					try {
						TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
						TResult<List<TPropertie>> res = serv
								.listAll(TedrosContext.getLoggedUser().getAccessToken(), TPropertie.class);
						if(res.getState().equals(TState.SUCCESS)) {
							List<TPropertie> l = res.getValue();
							List<Map<String, String>> lst = new ArrayList<>();
							l.forEach(c->{
								if( (c.getKey().equals(TSystemPropertie.SMTP_PASS.getValue()) && c.getValue()!=null)
										|| (c.getKey().equals(TSystemPropertie.OPENAI_KEY.getValue()) && c.getValue()!=null)
										|| (c.getKey().equals(TSystemPropertie.TOKEN.getValue()) && c.getValue()!=null)
										)
									c.setValue("*******");
								
								Map<String,String> m = new HashMap<>();
								m.put("name", c.getName());
								m.put("key", c.getKey());
								m.put("description", c.getDescription());
								m.put("value", c.getValue());
								
								if(c.getFile()!=null)
									m.put("file", "Property with file defined");
								lst.add(m);
							});
							return new Response("use the name field to help the user", lst);
						}
					}catch(Exception e) {
						LOGGER.error(e.getMessage(), e);
					}finally{
						loc.close();
					}
					return new Response("Cant retrieve the preference list!");
				});
	}
	
	@SuppressWarnings("rawtypes")
	public static TFunction<Empty> getModelBeingEditedFunction() {
		return new TFunction<>("get_edited_model", "Returns the entity model being edited by the user, "
				+ "call this to help the user with entered data", 
				Empty.class, 
				v->{
					TViewDescriptor vds = TedrosAppManager.getInstance().getCurrentViewDescriptor();
					ITView ov = TedrosAppManager.getInstance().getCurrentView();
					if(ov!=null) {
						ITDynaPresenter dp = (ITDynaPresenter) ov.gettPresenter();
						ITBehavior b = dp.getBehavior();
						if(b.getModelView()!=null)
							return new Response("Entity model from the view "+vds.getTitle(), b.getModelView().getModel());
					}
					return new Response("No model entities are being edited by the user!");
				});
	}
	
	public static TFunction<CallView> getViewModelFunction() {
		return new TFunction<>("get_model", 
			"Returns the entity model used in the viewPath, call this to get information about the model. "
			+ "Important: Before calling this, make sure that the viewPath exists, for that call the list_system_views function", 
			CallView.class, 
				v->{
					TViewDescriptor vds = TedrosAppManager.getInstance()
							.getViewDescriptor(v.getViewPath());
					if(vds!=null)
						return vds.getModel();
					return new Response("Entity model not found!");
				});
	}
	
	public static TFunction<Empty> listAllViewPathFunction() {
		
		List<ViewPath> lst = TedrosAppManager.getInstance().getAppContexts()
			.parallelStream()
			.flatMap(actx -> actx.getModulesContext().parallelStream())
			.flatMap(mctx -> mctx.getModuleDescriptor().getViewDescriptors().parallelStream())
			.map(vds -> new ViewPath(vds.getPath()))
			.sorted((v1, v2) -> v1.getViewPath().compareToIgnoreCase(v2.getViewPath()))
			.toList();
		
		return new TFunction<>("list_all_view_path", 
			"It lists all the view paths ('viewPath'), can be used to call up a view and to get more details about a specific view.", 
			Empty.class, obj->lst);	
	}
	
	public static TFunction<Empty> listAllAppsFunction() {
		
		AppCatalog log = new AppCatalog();
		
		// Paralelismo nos contextos de aplicativos
		TedrosAppManager.getInstance().getAppContexts()
			.parallelStream()
			.forEach(actx -> {
				// Paralelismo nos módulos
				List<ModuleInfo> mods = actx.getModulesContext()
					.parallelStream()
					.map(mctx -> {
						// Paralelismo nas views
						List<ViewInfo> views = mctx.getModuleDescriptor().getViewDescriptors()
							.parallelStream()
							.map(vds -> {
						
								Boolean viewAccess = vds.getSecurityDescriptor() != null
										? TedrosContext.isUserAuthorized(vds.getSecurityDescriptor(), 
												TAuthorizationType.VIEW_ACCESS)
										: true;
								
								return new ViewInfo(vds.getPath(), vds.getTitle(), vds.getDescription(), viewAccess.toString());
							})
							.toList();
				
						Boolean modAccess = mctx.getModuleDescriptor().getSecurityDescriptor() != null
								? TedrosContext.isUserAuthorized(mctx.getModuleDescriptor().getSecurityDescriptor(), 
										TAuthorizationType.MODULE_ACCESS)
								: true;
						
						return new ModuleInfo(mctx.getModuleDescriptor().getModuleName(), modAccess.toString(), views);
					})
					.toList();
				
				Boolean appAccess = actx.getAppDescriptor().getSecurityDescriptor() != null
						? TedrosContext.isUserAuthorized(actx.getAppDescriptor().getSecurityDescriptor(), 
								TAuthorizationType.APP_ACCESS)
						: true;
				
				log.add(actx.getAppDescriptor().getName(), appAccess.toString(), mods);
			});
		
		return new TFunction<Empty>("lists_all_applications", 
			"It lists all the applications and can be used to discover all the system's functionalities.", 
			Empty.class, obj->log);
	}
	
	public static TFunction<ViewPath> callUpViewFunction() {
		return new TFunction<>("call_view", 
			"Calls and opens a view using a 'viewPath'", 
			ViewPath.class, 
				v->{	
					
					LOGGER.info("Calling view path: {}", v.getViewPath());
					
					StringBuilder sb = new StringBuilder(v.getViewPath());
					TViewDescriptor vds = TedrosAppManager.getInstance()
							.getViewDescriptor(v.getViewPath());
					if(vds!=null) {
						Platform.runLater(()->{
							TedrosAppManager.getInstance()
							.goToModule(vds.getModuleDescriptor().getType(), vds.getModelView());
						});
						sb.append(" opened successfully!");
					}
					
					if(sb.toString().equals(v.getViewPath()))
						sb.append(" does not exist! Run the list_all_view_path function to find the correct 'viewPath'.");

					LOGGER.info("Result calling view path: {}, {}", v.getViewPath(), sb.toString());
					
				return new Response(sb.toString());
		});
	}
	
	public static TFunction<ViewPath> getViewInfoFunction() {
		return new TFunction<>("get_view_info", 
			"Gets information from a specific view, must be used with a correct 'viewPath' returned from the list_all_view_path function", 
			ViewPath.class, 
				v->{	
					TViewDescriptor vds = TedrosAppManager.getInstance()
							.getViewDescriptor(v.getViewPath());
					if(vds!=null) {
						Boolean viewAccess = vds.getSecurityDescriptor()!=null
								?TedrosContext.isUserAuthorized(vds.getSecurityDescriptor(), 
										TAuthorizationType.VIEW_ACCESS)
										: true;
						return new ViewInfo(vds.getPath(), vds.getTitle(), vds.getDescription(), viewAccess.toString());
					}
					
					StringBuilder sb = new StringBuilder(v.getViewPath());
					sb.append(" does not exist! Run the list_all_view_path function to find the correct 'viewPath'");
					
				return new Response(sb.toString());
		});
	}
	
	public static void main(String[] args) {
		 try {
			 
			//String dirPath = "C:/tmp/";
			 
			 //String fileName = "teste_file";
			 //String fullName = fileName + ".docx";
			 //File file = new File(dirPath+fullName);
			 
			 String encode = null;
			 try(FileInputStream is = new FileInputStream(new File("C:\\desenv\\tmp\\hosts.txt"))){
				 byte[] bytes = is.readAllBytes();
				 encode = Base64.getEncoder().encodeToString(bytes);
			 }
			 
			 CreateBinaryFile cbf = new CreateBinaryFile();
			 cbf.setBase64Content(encode);
			 cbf.setExtension("txt");
			 cbf.setName("teste_file");
			 cbf.setSubfolder("teste");
			 
			 TFunction<CreateBinaryFile> fn = TFunctionHelper.getCreateFileFunction();
			 
			 Function<CreateBinaryFile, Object> cb = fn.getCallback();
	         Object result = cb.apply(cbf);
	         
	         System.out.println(result);

			 /*
			 byte[] data = Base64.getDecoder().decode(encode);
			 FileUtils.writeByteArrayToFile(file, data);

			 String fullPath = file.getAbsolutePath();
			 LOGGER.info("File created successfully: {}", fullPath);
			 */
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}