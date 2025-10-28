/**
 * 
 */
package org.tedros.tools.module.notify.function;

import org.apache.commons.lang3.StringUtils;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.api.presenter.view.ITView;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.notify.model.TNotifyMV;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@Deprecated
public class CreateNotificationFunction {
/*
	@SuppressWarnings("unchecked")
	public CreateNotificationFunction() {
		super("set_notify", "Sets a  notify model in the view '"+ToolsKey.VIEW_NOTIFY+"'", Content.class, 
				v->{
					TedrosAppManager mng = TedrosAppManager.getInstance();
					TViewDescriptor vds = mng.getCurrentViewDescriptor();
					
					if(vds!=null && vds.getModel()==TNotify.class) {
						ITView<TDynaPresenter<TNotifyMV>> vw = mng.getCurrentView();
						TDynaPresenter<TNotifyMV> p = vw.gettPresenter();
						TNotifyMV mv = p.getBehavior().getModelView();
						if(mv!=null) {
							Platform.runLater(()->{
								SimpleStringProperty subject = mv.getProperty("subject");
								SimpleStringProperty content = mv.getProperty("content");
								SimpleStringProperty to = mv.getProperty("to");
								if(StringUtils.isNotBlank(v.getSubject()))
									subject.setValue(v.getSubject());
								if(StringUtils.isNotBlank(v.getContent()))
									content.setValue(v.getContent());
								if(StringUtils.isNotBlank(v.getTo()))
									to.setValue(v.getTo());
							});
						}else{
							Platform.runLater(()->{
								TNotify n = new TNotify();
								n.setSubject(v.getSubject());
								n.setContent(v.getContent());
								n.setTo(v.getTo());
								TNotifyMV mv0 = new TNotifyMV(n);
								p.getBehavior().setModelView(mv0);
								p.getBehavior().buildForm(TViewMode.EDIT);
							});
						}
					}else{
						Platform.runLater(()->{
							TNotify n = new TNotify();
							n.setSubject(v.getSubject());
							n.setContent(v.getContent());
							n.setTo(v.getTo());
							TNotifyMV mv = new TNotifyMV(n);
							mng.loadInModule(TNotifyModule.class, mv);
						});
						
					}

					return new Response("The operation was successful, the user can now send or schedule the sending!");
				});
	}*/

}
