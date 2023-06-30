/**
 * 
 */
package org.tedros.ai;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.CallView;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.function.model.ViewCatalog;
import org.tedros.api.presenter.ITDynaPresenter;
import org.tedros.api.presenter.behavior.ITBehavior;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;

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
	
	public static TFunction<CallView> callViewFunction() {
		return new TFunction<CallView>("call_view", 
			"Opens a view. Important: Before calling this, make sure the viewPath exists, for that call the function list_system_views", 
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
						sb.append(" cannot open or does not exist pass the correct view path!");
				return new Response(sb.toString());
		});
	}
	
	public static TFunction<Empty> listAllViewsFunction() {
		ViewCatalog log = new ViewCatalog();
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
		
	}

}
