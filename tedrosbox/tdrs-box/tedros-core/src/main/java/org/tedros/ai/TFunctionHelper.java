/**
 * 
 */
package org.tedros.ai;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.AppCatalog;
import org.tedros.ai.function.model.CallView;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.ModuleInfo;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.function.model.ViewInfo;
import org.tedros.ai.function.model.ViewPath;
import org.tedros.ai.model.CreateFile;
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
import org.tedros.core.service.remote.ServiceLocator;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TedrosFolder;

import javafx.application.Platform;

/**
 * @author Davis Gordon
 *
 */
public class TFunctionHelper {

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
					arr = ArrayUtils.add(arr, c.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return arr;
	}
	
	public static TFunction<CreateFile> getCreateFileFunction() {
		return new TFunction<CreateFile>("create_file", "Creates a file.", 
				CreateFile.class, 
				v->{
					
					String dir = TedrosFolder.EXPORT_FOLDER.getFullPath();
					String path = dir+v.getName()+"."+v.getExtension();
					File f = new File(path);
					try(OutputStream out = new FileOutputStream(f)) {
					IOUtils.write(v.getContent(), out, Charset.forName("UTF-8"));
					return new Response("File created! return file path like this: '!{"+path+"}'");
					} catch (Exception e) {
						e.printStackTrace();
						return new Response("Error: "+e.getMessage());
					}
					
					//return new Response("Cant create the file!");
				});
	}
	
	public static TFunction<Empty> getPreferencesFunction() {
		return new TFunction<Empty>("get_system_preferences", "Returns the system preferences for chat server, smtp server, "
				+ "view history page, openai, teros status, reports, notify, currency/date format and others", 
				Empty.class, 
				v->{
					ServiceLocator loc = ServiceLocator.getInstance();
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
						
					}finally{
						loc.close();
					}
					return new Response("Cant retrieve the preference list!");
				});
	}
	
	@SuppressWarnings("rawtypes")
	public static TFunction<Empty> getModelBeingEditedFunction() {
		return new TFunction<Empty>("get_edited_model", "Returns the entity model being edited by the user, "
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
		return new TFunction<CallView>("get_model", 
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
	
	/*public static TFunction<CallView> callViewFunction() {
		return new TFunction<CallView>("call_view_path", 
			"Opens a view. IMPORTANT: Before calling this make sure that the 'viewPath' exists, to do this call the list_system_views function.", 
			CallView.class, 
				v->{	
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
						sb.append(" does not exist! Run the list_system_views function to find the correct path.");
				return new Response(sb.toString());
		});
	}*/
	
	/*public static TFunction<Empty> listAllViewsFunction() {
		AppCatalog log = new AppCatalog();
		TedrosAppManager.getInstance().getAppContexts()
		.forEach(actx->{
			Boolean appAccess = actx.getAppDescriptor().getSecurityDescriptor()!=null
					? TedrosContext.isUserAuthorized(actx.getAppDescriptor().getSecurityDescriptor(), 
							TAuthorizationType.APP_ACCESS)
							: true;
			actx.getModulesContext().forEach(mctx->{
				Boolean modAccess = mctx.getModuleDescriptor().getSecurityDescriptor() != null
						? TedrosContext.isUserAuthorized(mctx.getModuleDescriptor().getSecurityDescriptor(), 
								TAuthorizationType.MODULE_ACCESS)
								: true;
						
				mctx.getModuleDescriptor().getViewDescriptors()
				.forEach(vds->{
					Boolean viwAccess = vds.getSecurityDescriptor()!=null
							?TedrosContext.isUserAuthorized(vds.getSecurityDescriptor(), 
									TAuthorizationType.VIEW_ACCESS)
									: true;
					log.add(mctx.getModuleDescriptor().getApplicationName(), mctx.getModuleDescriptor().getModuleName(), 
							vds.getTitle(), vds.getDescription(), vds.getPath(), appAccess.toString(), modAccess.toString(), viwAccess.toString());
				});
			});
		});
		return new TFunction<Empty>("list_system_views", 
			"Lists all system views, use this to see what the system can do and get a path to open a view", 
			Empty.class, obj->log);
		
	}*/
	
	public static TFunction<Empty> listAllViewPathFunction() {
		List<ViewPath> lst = new ArrayList<ViewPath>();
		TedrosAppManager.getInstance().getAppContexts()
		.forEach(actx->{
			actx.getModulesContext().forEach(mctx->{
				mctx.getModuleDescriptor().getViewDescriptors()
				.forEach(vds->{
					lst.add(new ViewPath(vds.getPath()));
				});
			});
		});
		return new TFunction<Empty>("list_all_view_path", 
			"It lists all the view paths ('viewPath'), can be used to call up a view and to get more details about a specific view.", 
			Empty.class, obj->lst);	
	}
	
	public static TFunction<Empty> listAllAppsFunction() {
		AppCatalog log = new AppCatalog();
		TedrosAppManager.getInstance().getAppContexts()
		.forEach(actx->{
			List<ModuleInfo> mods = new ArrayList<>();
			Boolean appAccess = actx.getAppDescriptor().getSecurityDescriptor()!=null
					? TedrosContext.isUserAuthorized(actx.getAppDescriptor().getSecurityDescriptor(), 
							TAuthorizationType.APP_ACCESS)
							: true;
			actx.getModulesContext().forEach(mctx->{
				List<ViewInfo> views = new ArrayList<>();
				Boolean modAccess = mctx.getModuleDescriptor().getSecurityDescriptor() != null
						? TedrosContext.isUserAuthorized(mctx.getModuleDescriptor().getSecurityDescriptor(), 
								TAuthorizationType.MODULE_ACCESS)
								: true;				
				mctx.getModuleDescriptor().getViewDescriptors()
				.forEach(vds->{
					Boolean viewAccess = vds.getSecurityDescriptor()!=null
							?TedrosContext.isUserAuthorized(vds.getSecurityDescriptor(), 
									TAuthorizationType.VIEW_ACCESS)
									: true;
					
					views.add(new ViewInfo(vds.getPath(), vds.getTitle(), vds.getDescription(), viewAccess.toString()));
				});
				mods.add(new ModuleInfo(mctx.getModuleDescriptor().getModuleName(), modAccess.toString(), views));
			});
			log.add(actx.getAppDescriptor().getName(), appAccess.toString(), mods);
		});
		return new TFunction<Empty>("lists_all_applications", 
			"It lists all the applications and can be used to discover all the system's functionalities.", 
			Empty.class, obj->log);
	}
	
	public static TFunction<ViewPath> callUpViewFunction() {
		return new TFunction<ViewPath>("call_view", 
			"Calls and opens a view using a 'viewPath'", 
			ViewPath.class, 
				v->{	
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
				return new Response(sb.toString());
		});
	}
	
	public static TFunction<ViewPath> getViewInfoFunction() {
		return new TFunction<ViewPath>("get_view_info", 
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

}
