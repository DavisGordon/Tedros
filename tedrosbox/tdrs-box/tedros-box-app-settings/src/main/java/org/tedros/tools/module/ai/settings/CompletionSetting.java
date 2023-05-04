/**
 * 
 */
package org.tedros.tools.module.ai.settings;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
import org.tedros.api.presenter.view.ITView;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.ai.model.TAiModel;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.security.model.TUser;
import org.tedros.fx.form.TSetting;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.CompletionMV;
import org.tedros.tools.module.ai.process.TAiCompletionProcess;

import javafx.concurrent.Worker.State;

/**
 * @author Davis Gordon
 *
 */
public class CompletionSetting extends TSetting {

	/**
	 * @param descriptor
	 */
	public CompletionSetting(ITComponentDescriptor descriptor) {
		super(descriptor);
	}

	/* (non-Javadoc)
	 * @see org.tedros.fx.form.TSetting#run()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void run() {
		final ITView view = super.getDescriptor().getForm().gettPresenter().getView();
		CompletionMV mv = super.getModelView();
		if(StringUtils.isBlank(mv.getResponse().getValue())) {
			TAiCompletion e = new TAiCompletion();
			
			TUser u = TedrosContext.getLoggedUser();
			String prompt = TLanguage.getInstance()
					.getFormatedString(ToolsKey.AI_WELCOME, u.getName());
	
			e.setUser(u.getName());
			e.setPrompt(prompt);
			e.setMaxTokens(256);
			e.setTemperature(0.4);
			e.setModel(TAiModel.TEXT_DAVINCI_003);
			
			TAiCompletionProcess p = new TAiCompletionProcess();
			p.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					TResult<TAiCompletion> res = p.getValue().get(0);
					if(res.getState().equals(TState.SUCCESS)) {
						TAiCompletion m = res.getValue();
						mv.getResponse().setValue(m.getResponse());
					}
				}
			});
			view.gettProgressIndicator().bind(p.runningProperty());
			p.completion(e);
			p.startProcess();
		}
	}


	/* (non-Javadoc)
	 * @see org.tedros.api.form.ITSetting#dispose()
	 */
	@Override
	public void dispose() {

	}
}
