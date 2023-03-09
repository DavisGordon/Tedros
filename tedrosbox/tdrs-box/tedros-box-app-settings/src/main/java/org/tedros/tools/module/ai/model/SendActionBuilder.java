/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.api.form.ITForm;
import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.control.TProgressIndicator;
import org.tedros.fx.builder.TBaseEventHandlerBuilder;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.tools.module.ai.process.TAiCompletionProcess;

import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
			CompletionMV mv = (CompletionMV) super.getComponentDescriptor().getModelView();
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
				}
			});
			p.completion(e);
			p.startProcess();
		};
	}


}
