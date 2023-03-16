/**
 * 
 */
package org.tedros.tools.module.ai.settings;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.descriptor.ITComponentDescriptor;
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

import javafx.animation.FadeTransition;
import javafx.concurrent.Worker.State;
import javafx.scene.Node;
import javafx.util.Duration;

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
	@Override
	public void run() {
		CompletionMV mv = super.getModelView();
		if(StringUtils.isBlank(mv.getResponse().getValue())) {
			Node control = getControl("response");
			FadeTransition ft = new FadeTransition(Duration.millis(2000), control );
		    ft.setFromValue(1.0);
		    ft.setToValue(0.3);
		    ft.setCycleCount(FadeTransition.INDEFINITE);
		    ft.setAutoReverse(true);
		    ft.play();
			
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
					ft.stop();
					control.setOpacity(100);
					TResult<TAiCompletion> res = p.getValue().get(0);
					if(res.getState().equals(TState.SUCCESS)) {
						TAiCompletion m = res.getValue();
						mv.getResponse().setValue(m.getResponse());
					}
				}
			});
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
