/**
 * 
 */
package org.tedros.tools.module.ai.model;

import org.tedros.core.ai.model.TAiCompletion;
import org.tedros.core.context.TedrosContext;
import org.tedros.core.controller.TAiCompletionController;
import org.tedros.core.controller.TAuthorizationController;
import org.tedros.core.security.model.TAuthorization;
import org.tedros.fx.annotation.control.TDoubleField;
import org.tedros.fx.annotation.control.TIntegerField;
import org.tedros.fx.annotation.control.TTextAreaField;
import org.tedros.fx.annotation.form.TForm;
import org.tedros.fx.annotation.presenter.TBehavior;
import org.tedros.fx.annotation.presenter.TDecorator;
import org.tedros.fx.annotation.presenter.TListViewPresenter;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.annotation.process.TEjbService;
import org.tedros.fx.annotation.view.TPaginator;
import org.tedros.fx.presenter.model.TEntityModelView;
import org.tedros.tools.ToolsKey;
import org.tedros.tools.module.user.action.TAuthorizationLoadAction;
import org.tedros.tools.module.user.behaviour.TAuthorizationBehavior;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "Ask Teros AI", showBreadcrumBar=true)
@TEjbService(serviceName = TAiCompletionController.JNDI_NAME, model=TAiCompletion.class)
@TListViewPresenter(listViewMinWidth=500,
	paginator=@TPaginator(entityClass = TAiCompletion.class, 
	serviceName = TAiCompletionController.JNDI_NAME, show=true),
	presenter=@TPresenter(decorator = @TDecorator(viewTitle=ToolsKey.VIEW_AUTHORIZATION)
	))
public class CompletionMV extends TEntityModelView<TAiCompletion> {

	private SimpleStringProperty title;
	
	@TTextAreaField
	private SimpleStringProperty response;
	
	@TTextAreaField
	private SimpleStringProperty prompt;
	
	@TDoubleField
	private SimpleDoubleProperty temperature;
	
	@TIntegerField
	private SimpleIntegerProperty maxTokens;
	
	
	/**
	 * 
	 */
	public CompletionMV(TAiCompletion m) {
		super(m);
		if(m.getUser()==null)
			m.setUser(TedrosContext.getLoggedUser());
		super.formatToString("%s", title);
		prompt.addListener((a,o,n)->{
			if(n!=null && n.length()>40) {
				title.setValue(n.substring(0, 34)+"...");
			}else 
				title.setValue(n);
		});
	}


	/**
	 * @return the response
	 */
	public SimpleStringProperty getResponse() {
		return response;
	}


	/**
	 * @param response the response to set
	 */
	public void setResponse(SimpleStringProperty response) {
		this.response = response;
	}


	/**
	 * @return the prompt
	 */
	public SimpleStringProperty getPrompt() {
		return prompt;
	}


	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(SimpleStringProperty prompt) {
		this.prompt = prompt;
	}


	/**
	 * @return the temperature
	 */
	public SimpleDoubleProperty getTemperature() {
		return temperature;
	}


	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(SimpleDoubleProperty temperature) {
		this.temperature = temperature;
	}


	/**
	 * @return the maxTokens
	 */
	public SimpleIntegerProperty getMaxTokens() {
		return maxTokens;
	}


	/**
	 * @param maxTokens the maxTokens to set
	 */
	public void setMaxTokens(SimpleIntegerProperty maxTokens) {
		this.maxTokens = maxTokens;
	}


	/**
	 * @return the title
	 */
	public SimpleStringProperty getTitle() {
		return title;
	}


	/**
	 * @param title the title to set
	 */
	public void setTitle(SimpleStringProperty title) {
		this.title = title;
	}


}
