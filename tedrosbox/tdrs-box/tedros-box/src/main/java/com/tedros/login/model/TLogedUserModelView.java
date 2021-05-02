/**
 * 
 */
package com.tedros.login.model;

import com.tedros.core.annotation.security.TAuthorizationType;
import com.tedros.core.annotation.security.TSecurity;
import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.fxapi.annotation.TObservableValue;
import com.tedros.fxapi.annotation.control.TComboBoxField;
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
import com.tedros.fxapi.annotation.property.TReadOnlyBooleanProperty;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.text.TText;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.domain.THtmlConstant;
import com.tedros.fxapi.domain.TStyleParameter;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.behavior.TSaveViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;
import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;
import com.tedros.settings.security.model.TProfileModelView;
import com.tedros.settings.security.process.TUserProcess;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
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
			decorator=@TDecorator(type = TSaveViewDecorator.class, 
			viewTitle="#{security.user.view.title}", buildModesRadioButton=false),
			behavior=@TBehavior(type=TSaveViewBehavior.class))
@TEntityProcess(entity=TUser.class, process = TUserProcess.class)
public class TLogedUserModelView extends TEntityModelView<TUser> {

	private SimpleLongProperty id;
	
	@TFieldBox(alignment=Pos.CENTER_LEFT, node=@TNode(id="t-pane", parse = true))
	@TText(text="#{label.user.header}", font=@TFont(size=22), textAlignment=TextAlignment.LEFT, 
		node=@TNode(id="t-form-title-text", parse = true))
	private SimpleStringProperty header;
	
	
	@TLabel(text = "#{tedros.profile}")
	@TComboBoxField(firstItemTex="#{tedros.select}", 
		node=@TNode(disable=false, parse=true))
	private SimpleObjectProperty<TProfileModelView> activeProfile;
	
	
	public TLogedUserModelView(TUser entity) {
		super(entity);
	}

	
	
	@Override
	public void reload(TUser model) {
		super.reload(model);
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

	

	public SimpleStringProperty getHeader() {
		return header;
	}

	public void setHeader(SimpleStringProperty header) {
		this.header = header;
	}


	/**
	 * @return the activeProfile
	 */
	public SimpleObjectProperty<TProfileModelView> getActiveProfile() {
		return activeProfile;
	}

	/**
	 * @param activeProfile the activeProfile to set
	 */
	public void setActiveProfile(SimpleObjectProperty<TProfileModelView> activeProfile) {
		this.activeProfile = activeProfile;
	}



	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return header;
	}

	
}
