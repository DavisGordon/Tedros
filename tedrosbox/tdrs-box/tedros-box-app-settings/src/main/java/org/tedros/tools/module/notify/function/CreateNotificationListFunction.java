/**
 * 
 */
package org.tedros.tools.module.notify.function;

import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.notify.model.TNotifyMV;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class CreateNotificationListFunction extends TFunction<Contents> {

	@SuppressWarnings("unchecked")
	public CreateNotificationListFunction() {
		super("set_notify_list", "Sets a list of notify model in the view '"+ToolsKey.VIEW_NOTIFY+"'", Contents.class, 
				v->{
					TedrosAppManager mng = TedrosAppManager.getInstance();
					TViewDescriptor vds = mng.getCurrentViewDescriptor();
					
					if(vds!=null && vds.getModel()==TNotify.class) {
						ITView<TDynaPresenter<TNotifyMV>> vw = mng.getCurrentView();
						TDynaPresenter<TNotifyMV> p = vw.gettPresenter();
						
						Platform.runLater(()->{
							ObservableList<TNotifyMV> lst = FXCollections.observableArrayList();
							v.getList().forEach(c->{
								TNotify n = new TNotify();
								n.setSubject(c.getSubject());
								n.setContent(c.getContent());
								n.setTo(c.getTo());
								TNotifyMV mv0 = new TNotifyMV(n);
								lst.add(mv0);
							});
							
							p.getBehavior().loadModelViewList(lst);
						});
						
					}else{
						Platform.runLater(()->{
							ObservableList<TNotifyMV> lst = FXCollections.observableArrayList();
							v.getList().forEach(c->{
								TNotify n = new TNotify();
								n.setSubject(c.getSubject());
								n.setContent(c.getContent());
								n.setTo(c.getTo());
								TNotifyMV mv0 = new TNotifyMV(n);
								lst.add(mv0);
							});
							mng.loadInModule(TNotifyModule.class, lst);
						});
						
					}

					return new Response("The operation was successful, the user can now send or schedule the sending!");
				});
	}

}
