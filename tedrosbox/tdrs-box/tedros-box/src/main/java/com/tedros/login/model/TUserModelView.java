/**
 * 
 */
package com.tedros.login.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.control.TFieldBox;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewCollectionType;
import com.tedros.fxapi.annotation.control.TPasswordField;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.process.TEntityProcess;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.process.TUserProcess;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

/**
 * @author Davis Gordon
 *
 */
@TForm(name="#{security.user.form.name}")
@TFormReaderHtml
@TPresenter(type=TDynaPresenter.class, 
			decorator=@TDecorator(type = TSaveViewDecorator.class, viewTitle="#{security.user.view.title}", 
			listTitle="#{security.user.list.title}", buildModesRadioButton=false ),
			behavior=@TBehavior(type=TSaveViewBehavior.class))
@TEntityProcess(entity=TUser.class, process = TUserProcess.class)
public class TUserModelView extends TEntityModelView<TUser> {

	private SimpleLongProperty id;
	

	@TLabel(text="#{label.name}:")
	@TTextField(maxLength=100, required=true)
	private SimpleStringProperty name;
	
	
	@TLabel(text="#{label.userLogin}:")
	@TTextField(maxLength=100, required=true)
	@THBox(	pane=@TPane(children={"login","password"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="login", priority=Priority.NEVER), 
		   		@TPriority(field="password", priority=Priority.NEVER)}))
	private SimpleStringProperty login;
	
	@TLabel(text="#{label.password}:")
	@TPasswordField(required=true/*, 
		node=@TNode(focusedProperty=@TReadOnlyBooleanProperty(
				observableValue=@TObservableValue(addListener=TEncriptPasswordChangeListener.class), 
				parse = true), 
		parse = true)*/)
	private SimpleStringProperty password;
	
	
	@TModelViewCollectionType(modelClass=TProfile.class, modelViewClass=TProfileModelView.class, required=true)
	private ITObservableList<TProfileModelView> profiles;

	
	private SimpleStringProperty lastPassword;
	
	public TUserModelView(TUser entity) {
		super(entity);
		copyPassword();
		
		/*TimelineBuilder.create().keyFrames(
	            new KeyFrame(Duration.millis(20000), 
	                new EventHandler<ActionEvent>() {
	                    public void handle(ActionEvent t) {
	                    	profiles.add(new TProfileModelView(new TProfile(RandomStringUtils.randomAlphanumeric(4), RandomStringUtils.randomAlphanumeric(12))));
	                    }
	                })
	        )
	        .cycleCount(Animation.INDEFINITE)
	        .build()
	        .play();*/
	}

	private void copyPassword() {
		if(password!=null && password.getValue()!=null)
			lastPassword.setValue(password.getValue());
	}
	
	@Override
	public void reload(TUser model) {
		super.reload(model);
		copyPassword();
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#setId(javafx.beans.property.SimpleLongProperty)
	 */
	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#getId()
	 */
	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.presenter.model.TModelView#getDisplayProperty()
	 */
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return name;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getLogin() {
		return login;
	}

	public void setLogin(SimpleStringProperty login) {
		this.login = login;
	}

	public SimpleStringProperty getPassword() {
		return password;
	}

	public void setPassword(SimpleStringProperty password) {
		this.password = password;
	}
	
	public SimpleStringProperty getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(SimpleStringProperty lastPassword) {
		this.lastPassword = lastPassword;
	}

	/**
	 * @return the profiles
	 */
	public ITObservableList<TProfileModelView> getProfiles() {
		return profiles;
	}

	/**
	 * @param profiles the profiles to set
	 */
	public void setProfiles(ITObservableList<TProfileModelView> profiles) {
		this.profiles = profiles;
	}


}
