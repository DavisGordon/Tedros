/**
 * 
 */
package org.tedros.tools.module.notify.function;

import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.openai.model.ToolCallResult;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.context.TViewDescriptor;
import org.tedros.core.context.TedrosAppManager;
import org.tedros.core.notify.model.TNotify;
import org.tedros.fx.presenter.dynamic.TDynaPresenter;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.notify.TNotifyModule;
import org.tedros.tools.module.notify.model.TNotifyMV;
import org.tedros.util.TLoggerUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Davis Gordon
 *
 */
public class CreateNotificationListFunction extends TFunction<Contents> {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(CreateNotificationListFunction.class);
	
	public static final String NAME = "create_list_of_drafts_email";
	public static final String PROMPT = "Drafts and prepares email notifications for user review. " +
            "This tool does NOT send emails immediately. It opens a validation screen (view '" 
			+ TLanguage.getInstance().getString(ToolsKey.VIEW_NOTIFY) + "') " +
            "listing the generated content so the user can check and manually send them.";

	@SuppressWarnings("unchecked")
	public CreateNotificationListFunction() {
		super(NAME, PROMPT, 
			Contents.class, 
			v->{
				
				LOGGER.info("Creating notification list with {} items.", v.getList().size());
				
				//Gets the view descriptor of the currently open view, if any.
				TedrosAppManager mng = TedrosAppManager.getInstance();
				TViewDescriptor vds = mng.getCurrentViewDescriptor();
				
				if(vds!=null && vds.getModel()==TNotify.class) { // Is Notify the current view? 
					// Gets the presenter
					ITView<TDynaPresenter<TNotifyMV>> vw = mng.getCurrentView();
					TDynaPresenter<TNotifyMV> p = vw.gettPresenter();
					
					Platform.runLater(()->{
						try {
							ObservableList<TNotifyMV> lst = createNotifyList(v);
							p.getBehavior().loadModelViewList(lst); // loads list in current view
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					});
					
				}else{
					Platform.runLater(()->{
						try {
							ObservableList<TNotifyMV> lst = createNotifyList(v);
							mng.loadInModule(TNotifyModule.class, lst); //calls the module, opens the view and loads the list
						} catch (Exception e) {
							LOGGER.error(e.getMessage(), e);
						}
					});
					
				}
				
				return ToolCallResult.builder()
						.message("Drafts created successfully")
						.result(Map.of(
			                    STATUS, SUCCESS,
			                    "drafts_created_count", v.getList().size(),
			                    ACTION, "user_validation_screen_opened",
			                    INFO_MESSAGE, CONTENT_LOADED_IN_VIEW_FOR_USER_REVIEW_DO_NOT_RETRY
			                ))
						.build();
			});
	}

	private static ObservableList<TNotifyMV> createNotifyList(Contents v) {
		ObservableList<TNotifyMV> lst = FXCollections.observableArrayList();
		v.getList().forEach(c->{
			TNotify n = new TNotify();
			n.setSubject(c.getSubject());
			n.setContent(c.getContent());
			n.setTo(c.getTo());
			TNotifyMV mv0 = new TNotifyMV(n);
			lst.add(mv0);
		});
		return lst;
	}

}
