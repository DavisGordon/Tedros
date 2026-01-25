/**
 * 
 */
package org.tedros.ai.function;

import static org.tedros.ai.function.TFunction.ACTION;
import static org.tedros.ai.function.TFunction.ERROR;
import static org.tedros.ai.function.TFunction.ERROR_MESSAGE;
import static org.tedros.ai.function.TFunction.STATUS;
import static org.tedros.ai.function.TFunction.SUCCESS;
import static org.tedros.ai.function.TFunction.SYSTEM_INSTRUCTION;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.tedros.ai.function.model.AppCatalog;
import org.tedros.ai.function.model.CallView;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.ModuleInfo;
import org.tedros.ai.function.model.ViewInfo;
import org.tedros.ai.function.model.ViewPath;
import org.tedros.ai.model.CreateBinaryFile;
import org.tedros.api.presenter.ITDynaPresenter;
import org.tedros.api.presenter.behavior.ITBehavior;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TReflections;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TPropertieController;
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
                    
                    return ToolCallResult.builder()
							.message("File created successfully.")
							.result(Map.of(
			                    STATUS, SUCCESS,
			                    ACTION, "file_created",
			                    SYSTEM_INSTRUCTION, "File created successfully! "
			                    		+ "Do not retry again. Inform the user to check the created file.",
			                    "path", "!"+ fullPath.replace("\\", "\\\\")
			                ))
							.build();
                    
                } catch (Exception e) {
                    LOGGER.error("Failed to create file {}.{}: {}", 
                        request.getName(), request.getExtension(), e.getMessage(), e);
                    return ToolCallResult.builder()
							.message("Error creating file: " + e.getMessage())
							.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "file_creation_failed",
			                    ERROR_MESSAGE, "Error creating file: " + e.getMessage()
			                ))
							.build();
                }
            });
    }
	
	public static TFunction<Empty> getPreferencesFunction() {
		return new TFunction<>("get_system_preferences", "Returns the system preferences for chat server, smtp server, "
				+ "view history page, openai, teros status, reports, notify, currency/date format and others", 
				Empty.class, 
				v->{
					try(TEjbServiceLocator loc = TEjbServiceLocator.getInstance()) {
						TPropertieController serv = loc.lookup(TPropertieController.JNDI_NAME);
						TResult<List<TPropertie>> res = serv
								.listAll(TedrosContext.getLoggedUser().getAccessToken(), TPropertie.class);
						if(res.getState().equals(TState.SUCCESS)) {
							List<TPropertie> l = res.getValue();
							List<Map<String, String>> lst = new ArrayList<>();
							l.forEach(c->{
								if(StringUtils.containsAny(c.getKey(), "KEY", "PASS", "TOKEN") && c.getValue()!=null)
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
							
							return ToolCallResult.builder()
									.message("Preferences retrieved successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "preferences_retrieved",
						                    SYSTEM_INSTRUCTION, "Preferences retrieved successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "preferences", lst
						                ))
									.build();
						}
					}catch(Exception e) {
						LOGGER.error(e.getMessage(), e);
						return ToolCallResult.builder()
								.message("Error retrieving preferences: " + e.getMessage())
								.result(Map.of(
					                    STATUS, ERROR,
					                    ACTION, "preferences_retrieval_failed",
					                    ERROR_MESSAGE, e.getMessage()
					                ))
								.build();
					}
					
					return ToolCallResult.builder()
							.message("No preferences found.")
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "no_preferences_found",
				                    ERROR_MESSAGE, "No preferences available in the system."
				                ))
							.build();
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
						if(b.getModelView()!=null) {
							return ToolCallResult.builder()
									.message("Entity model being edited retrieved successfully.")
									.result(Map.of(
						                    STATUS, SUCCESS,
						                    ACTION, "edited_model_retrieved",
						                    SYSTEM_INSTRUCTION, "Edited model retrieved successfully. "
						                    		+ "Do not retry again. Proceed with the user's request.",
						                    "model", b.getModelView().getModel()
						                ))
									.build();
						}	
					}
					return ToolCallResult.builder()
							.message("No model being edited found.")
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "no_edited_model_found",
				                    ERROR_MESSAGE, "No model being edited found in the current view: " 
				                    		+ (vds!=null?vds.getPath(): "No view descriptor"))
				                )
							.build();
				});
	}
	
	public static TFunction<CallView> getViewModelFunction() {
		return new TFunction<>("get_model", 
			"Returns the entity model used in the viewPath, call this to get information about the model. "
			+ "Important: Before calling this, make sure that the viewPath exists, for that call the list_all_view_path function", 
			CallView.class, 
				v->{
					TViewDescriptor vds = TedrosAppManager.getInstance()
							.getViewDescriptor(v.getViewPath());
					if(vds!=null) {
						return ToolCallResult.builder()
								.message("Entity model retrieved successfully.")
								.result(Map.of(
					                    STATUS, SUCCESS,
					                    ACTION, "entity_model_retrieved",
					                    SYSTEM_INSTRUCTION, "Entity model retrieved successfully. "
					                    		+ "Do not retry again. Proceed with the user's request.",
					                    "model", vds.getModel()
					                ))
								.build();
					}
						
					return ToolCallResult.builder()
							.message("View path does not exist.")
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "view_path_not_found",
				                    ERROR_MESSAGE, "The view path " + v.getViewPath() + " does not exist. "
				                    		+ "Run the list_all_view_path function to find the correct viewPath.")
				                )
							.build();
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
			Empty.class, obj->ToolCallResult.builder()
					.message("View paths listed successfully.")
					.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "view_paths_listed",
		                    SYSTEM_INSTRUCTION, "View paths listed successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "view_paths", lst
		                ))
					.build());	
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
		
		return new TFunction<>("lists_all_applications", 
			"It lists all the applications and can be used to discover all the system's functionalities.", 
			Empty.class, obj->ToolCallResult.builder()
					.message("Applications listed successfully.")
					.result(Map.of(
		                    STATUS, SUCCESS,
		                    ACTION, "applications_listed",
		                    SYSTEM_INSTRUCTION, "Applications listed successfully. "
		                    		+ "Do not retry again. Proceed with the user's request.",
		                    "applications", log
		                ))
					.build());
	}
	
	public static TFunction<ViewPath> callUpViewFunction() {
		return new TFunction<>("call_view", 
			"Calls and opens a view using a 'viewPath'", 
			ViewPath.class, 
				v->{	
					
					LOGGER.info("Calling view path: {}", v.getViewPath());
					
					TViewDescriptor vds = TedrosAppManager.getInstance()
							.getViewDescriptor(v.getViewPath());
					
					if(vds!=null) {
						Platform.runLater(()->
							TedrosAppManager.getInstance()
							.goToModule(vds.getModuleDescriptor().getType(), vds.getModelView())
						);
						
						return ToolCallResult.builder()
								.message("View called successfully.")
								.result(Map.of(
					                    STATUS, SUCCESS,
					                    ACTION, "view_called",
					                    SYSTEM_INSTRUCTION, "View called successfully. "
					                    		+ "Do not retry again. Inform the user to check the opened view."
					                ))
								.build();
					}
					
				return ToolCallResult.builder()
						.message("View path does not exist.")
						.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "view_path_not_found",
			                    ERROR_MESSAGE, "The view path " + v.getViewPath() + " does not exist. "
			                    		+ "Run the list_all_view_path function to find the correct viewPath.")
			                )
						.build();
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
								? TedrosContext.isUserAuthorized(vds.getSecurityDescriptor(), 
										TAuthorizationType.VIEW_ACCESS)
										: true;
						
						ViewInfo viewInfo = new ViewInfo(vds.getPath(), vds.getTitle(), vds.getDescription(), viewAccess.toString());
						return ToolCallResult.builder()
								.message("View information retrieved successfully.")
								.result(Map.of(
					                    STATUS, SUCCESS,
					                    ACTION, "view_info_retrieved",
					                    SYSTEM_INSTRUCTION, "View information retrieved successfully. "
					                    		+ "Do not retry again. Proceed with the user's request.",
					                    "view_info", viewInfo
					                ))
								.build();
					}
					
				return ToolCallResult.builder()
						.message("View path does not exist.")
						.result(Map.of(
			                    STATUS, ERROR,
			                    ACTION, "view_path_not_found",
			                    ERROR_MESSAGE, "The view path " + v.getViewPath() + " does not exist. "
			                    		+ "Run the list_all_view_path function to find the correct viewPath.")
			                )
						.build();
		});
	}
}