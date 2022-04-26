/**
 * 
 */
package org.somos.module.acao.campanha.model;

import org.somos.model.Campanha;

import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextField;
import com.tedros.fxapi.annotation.control.TTextInputControl;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.presenter.TSelectionModalPresenter;
import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.annotation.view.TPaginator;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.presenter.modal.behavior.TSelectionModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TSelectionModalDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "Selecionar Campanha")
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = Campanha.class, modelViewClass=CampanhaItemTableView.class, 
			serviceName = "ICampanhaControllerRemote"),
		presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
			decorator = @TDecorator(type=TSelectionModalDecorator.class, 
			viewTitle="Selecionar Campanha")),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="id", text = "Codigo", prefWidth=20, resizable=true), 
						@TTableColumn(cellValue="titulo", text = "Titulo", resizable=true), 
						@TTableColumn(cellValue="status", text = "Status", resizable=true)}), 
		allowsMultipleSelections = false)
public class CampanhaFindModelView extends TEntityModelView<Campanha> {
	
	@TLabel(text="Codigo:", position=TLabelPosition.LEFT)
	@TLongField(maxLength=6 )
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	@TLabel(text="Titulo")
	@TTextField(maxLength=100, 
	textInputControl=@TTextInputControl(promptText="Insira um titulo ou o local", parse = true),
	required=false)
	@THBox(	pane=@TPane(children={"titulo","data"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.SOMETIMES), 
   				   		@TPriority(field="data", priority=Priority.NEVER) }))
	private SimpleStringProperty titulo;
	
	public CampanhaFindModelView(Campanha entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Campanha model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Campanha model) {
		if(!model.isNew()){
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (titulo.getValue()!=null ? titulo.getValue() : "") ;
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener idListener =  super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = new ChangeListener<Long>(){
		
				public void changed(ObservableValue arg0, Long arg1, Long arg2) {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") ;
					displayText.setValue(str);
				}
			};
			super.addListener("displayText", idListener);
		}else
			id.removeListener(idListener);
		
		id.addListener(idListener);
		
		ChangeListener<String> tituloListener = super.getListenerRepository().get("displayText1");
		if(tituloListener==null){
			tituloListener = new ChangeListener<String>(){
				@Override
				public void changed(ObservableValue arg0, String arg1, String arg2) {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (arg2!=null ? arg2 : "") ;
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", tituloListener);
		}else
			titulo.removeListener(tituloListener);
		
		titulo.addListener(tituloListener);
		
	}
	
	
	public CampanhaFindModelView() {
		super(new Campanha());
	}

	@Override
	public void setId(SimpleLongProperty id) {
		this.id = id;
		
	}

	@Override
	public SimpleLongProperty getId() {
		return id;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return displayText;
	}

	
	
	public CampanhaFindModelView getNewInstance(){
		return new CampanhaFindModelView(new Campanha());
	}
	
	@Override
	public String toString() {
		return (getTitulo()!=null)? getTitulo().getValue() : "";	
	}

	/**
	 * @return the titulo
	 */
	public SimpleStringProperty getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(SimpleStringProperty titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the displayText
	 */
	public SimpleStringProperty getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(SimpleStringProperty displayText) {
		this.displayText = displayText;
	}


}
