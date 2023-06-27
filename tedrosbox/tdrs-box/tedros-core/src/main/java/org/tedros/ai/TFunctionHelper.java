/**
 * 
 */
package org.tedros.ai;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.CallView;
import org.tedros.ai.function.model.Empty;
import org.tedros.ai.function.model.Response;
import org.tedros.ai.function.model.ViewCatalog;
import org.tedros.core.annotation.security.TAuthorizationType;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.context.TedrosContext;

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
	
	public static TFunction<CallView> createCallViewFunction() {
		return new TFunction<CallView>("call_view", "Call and open a view/screen", CallView.class, 
				v->{	
					StringBuilder sb = new StringBuilder(v.getViewPath());
					TedrosAppManager.getInstance().getAppContexts()
					.forEach(actx->{
						actx.getModulesContext().forEach(mctx->{	
							mctx.getModuleDescriptor().getViewDescriptors()
							.forEach(vds->{
								if(vds.getPath().equals(v.getViewPath())) {
									TedrosAppManager.getInstance()
									.goToModule(mctx.getModuleDescriptor().getType(), vds.getModelView());
									sb.append(" opened successfully!");
								}
							});
						});
					});
					if(sb.toString().equals(v.getViewPath()))
						sb.append(" cannot open or does not exist pass the correct view path!");
				return new Response(sb.toString());
		});
	}
	
	public static TFunction<Empty> createListViewFunction() {
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
		return new TFunction<Empty>("list_system_views", "List all system views/screens", Empty.class, obj->log);
		
	}

}
