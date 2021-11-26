/**
 * 
 */
package com.tedros.fxapi.control;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.core.module.TObjectRepository;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFormBuilder;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.presenter.model.TModelView;
import com.tedros.fxapi.util.TModelViewUtil;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**<pre>
 * The detail field component open a form to add or remove item.
 * </pre>
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TDetailField extends TRequiredDetailField {

	private Button tNewButton;
	private Button tClearButton;
	private BorderPane pane;
	private StackPane screenSaverPane;
	
	private ITModelForm form;
	private Property<TModelView> tDetailProperty;
	private TObjectRepository repository = new TObjectRepository();
	private Class<? extends TModelView> tModelViewClass;
	private Class<? extends ITModel> tModelClass;
	
	
	public TDetailField(Class<? extends TModelView> tModelViewClass,
			Class<? extends ITModel> tModelClass, Property<TModelView> detail, boolean showButtons) {
		this.tModelClass=tModelClass;
		this.tModelViewClass=tModelViewClass;
		
		this.pane = new BorderPane();
		this.tDetailProperty = detail;
		if(showButtons) {
			TInternationalizationEngine iEngine = TInternationalizationEngine.getInstance(null);
			ToolBar bar = new ToolBar();
			bar.setId("t-view-toolbar");
			this.tClearButton = new Button();
			this.tNewButton = new Button();
			tNewButton.setId("t-button");
			tClearButton.setId("t-last-button");
			tNewButton.setText(iEngine.getString("#{tedros.fxapi.button.new}"));
			tClearButton.setText(iEngine.getString("#{tedros.fxapi.button.clean}"));
			bar.getItems().addAll(tNewButton, tClearButton);
			pane.setTop(bar);
			EventHandler<ActionEvent> nEv = e -> {
				this.tNewAction();
			};
			this.repository.add("nEv", nEv);
			this.tNewButton.setOnAction(new WeakEventHandler<>(nEv));
			EventHandler<ActionEvent> cEv = e -> {
				this.tClearAction();
			};
			this.repository.add("cEv", cEv);
			this.tClearButton.setOnAction(new WeakEventHandler<>(cEv));
		}
		
		ChangeListener<TModelView> chl = (a,b,n)->{
			if(n==null)
				clearForm();
			else
				showForm(n);
		};
		this.repository.add("chl", chl);
		this.tDetailProperty.addListener(new WeakChangeListener<>(chl));
		getChildren().add(pane);
		if(this.tDetailProperty.getValue()!=null)
			this.showForm(this.tDetailProperty.getValue());
	}

	/**
	 * @param n
	 */
	private void showForm(TModelView n) {
		form = TFormBuilder.create(n).build();
		this.pane.setCenter((Node) form);
	}
	
	@SuppressWarnings("unchecked")
	public void tNewAction(){
		TModelView m = TModelViewUtil.buildModelView(this.tModelViewClass, this.tModelClass);
		this.tDetailProperty.setValue(m);
	}
	
	public void tClearAction() {
		this.tDetailProperty.setValue(null);
	}

	private void clearForm() {
		this.tShowScreenSaver();
		if(form!=null) {
			form.gettModelView().removeAllListener();
			form.tDispose();
		}
		form=null;
	}
	
	private StackPane getScreenSaverPane() {
		if(screenSaverPane==null){
			screenSaverPane = new StackPane();
			screenSaverPane.setId("t-form-pane");
		}
		return screenSaverPane;
	}
	
	
	public void tShowScreenSaver() {
		pane.setCenter(getScreenSaverPane());
	}

	@Override
	public Node gettComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITObservableList<TModelView> gettSelectedItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
