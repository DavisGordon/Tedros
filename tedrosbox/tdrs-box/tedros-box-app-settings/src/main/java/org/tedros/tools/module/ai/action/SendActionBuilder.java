/**
 * 
 */
package org.tedros.tools.module.ai.action;

import org.apache.commons.lang3.StringUtils;
import org.tedros.api.form.ITForm;
import org.tedros.core.TLanguage;
import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.control.PopOver;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.core.control.PopOver.ArrowLocation;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;
import org.tedros.fx.control.TLabel;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.ai.model.CompletionMV;
import org.tedros.tools.module.ai.process.TAiCompletionProcess;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * @author Davis Gordon
 *
 */
public class SendActionBuilder extends TBaseEventHandlerBuilder<ActionEvent> {

	@Override
	public EventHandler<ActionEvent> build() {
		
		return ev -> {
			ITForm f = super.getComponentDescriptor().getForm();
			Button btn = (Button) ev.getSource();
			btn.setDisable(true);
			CompletionMV mv = (CompletionMV) super.getComponentDescriptor().getModelView();
			
			String prompt = mv.getPrompt().getValue();
			if(StringUtils.isBlank(prompt)) {
				Node input = super.getComponentDescriptor().getFieldDescriptor("prompt").getControl();
				PopOver pov = new PopOver();
				pov.setAutoHide(true);
				pov.setArrowLocation(ArrowLocation.BOTTOM_CENTER);
				pov.setContentNode(new TLabel(TLanguage.getInstance()
						.getString(ToolsKey.MESSAGE_AI_PROMPT_REQUIRED)));
				pov.show(input);
				btn.setDisable(false);
				return;
			}
			TAiCompletion e = mv.getEntity();
			
			TAiCompletionProcess p = new TAiCompletionProcess();
			TProgressIndicator pi = new TProgressIndicator((Pane) f);
			pi.bind(p.runningProperty());
			p.stateProperty().addListener((a,o,n)->{
				if(n.equals(State.SUCCEEDED)) {
					TResult<TAiCompletion> res = p.getValue().get(0);
					if(res.getState().equals(TState.SUCCESS)) {
						TAiCompletion m = res.getValue();
						mv.reload(m);
					}
					btn.setDisable(false);
				}
			});
			p.completion(e);
			p.startProcess();
		};
	}


}
