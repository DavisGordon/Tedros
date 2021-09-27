/**
 * 
 */
package com.covidsemfome.module.acao.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.covidsemfome.model.Acao;
import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellValueFactory;
import com.tedros.fxapi.annotation.control.TDatePickerField;
import com.tedros.fxapi.annotation.control.THorizontalRadioGroup;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TLabelDefaultSetting;
import com.tedros.fxapi.annotation.control.TLongField;
import com.tedros.fxapi.annotation.control.TRadioButtonField;
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
import com.tedros.fxapi.builder.DateTimeFormatBuilder;
import com.tedros.fxapi.domain.TLabelPosition;
import com.tedros.fxapi.presenter.modal.behavior.TSelectionModalBehavior;
import com.tedros.fxapi.presenter.modal.decorator.TSelectionModalDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TForm(name = "Selecionar Ação / Campanha")
@TLabelDefaultSetting(font=@TFont(size=12))
@TSelectionModalPresenter(
		paginator=@TPaginator(entityClass = Acao.class, modelViewClass=AcaoItemTableView.class, 
			serviceName = "IAcaoControllerRemote"),
		presenter=@TPresenter(behavior = @TBehavior(type = TSelectionModalBehavior.class), 
			decorator = @TDecorator(type=TSelectionModalDecorator.class, 
			viewTitle="Selecionar Ação / Campanha")),
		tableView=@TTableView(editable=true, 
			columns = { @TTableColumn(cellValue="id", text = "Codigo", prefWidth=20, resizable=true), 
						@TTableColumn(cellValue="data", text = "Data/Hora", prefWidth=40, resizable=true,
						cellValueFactory=@TCellValueFactory(parse=true, value=@TCallbackFactory(parse=true, value=AcaoDateCellCallBack.class))),
						@TTableColumn(cellValue="titulo", text = "Titulo / Local", resizable=true), 
						@TTableColumn(cellValue="status", text = "Status", resizable=true)}), 
		allowsMultipleSelections = false)
public class AcaoFindModelView extends TEntityModelView<Acao> {
	
	@TLabel(text="Codigo:", position=TLabelPosition.LEFT)
	@TLongField(maxLength=6 )
	private SimpleLongProperty id;
	
	private SimpleStringProperty displayText = new SimpleStringProperty();
	
	
	@TLabel(text="Titulo/Local")
	@TTextField(maxLength=100, 
	textInputControl=@TTextInputControl(promptText="Insira um titulo ou o local", parse = true),
	required=false)
	@THBox(	pane=@TPane(children={"titulo","data"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="titulo", priority=Priority.SOMETIMES), 
   				   		@TPriority(field="data", priority=Priority.NEVER) }))
	private SimpleStringProperty titulo;
	
	@TLabel(text="Data e Hora")
	@TDatePickerField(required = false, dateFormat=DateTimeFormatBuilder.class)
	private SimpleObjectProperty<Date> data;
	
	@TLabel(text="Status")
	@THorizontalRadioGroup(required=false, alignment=Pos.CENTER_LEFT, spacing=4, 
	radioButtons={
			@TRadioButtonField(text = "Agendada", userData = "Agendada"),
			@TRadioButtonField(text = "Cancelada", userData = "Cancelada"), 
			@TRadioButtonField(text = "Executada", userData = "Executada")
			})
	private SimpleStringProperty status;
	
	public AcaoFindModelView(Acao entity) {
		super(entity);
		buildListener();
		loadDisplayText(entity);
	}
	
	@Override
	public void reload(Acao model) {
		super.reload(model);
		buildListener();
		loadDisplayText(model);
	}

	/**
	 * @param model
	 */
	private void loadDisplayText(Acao model) {
		if(!model.isNew()){
			String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
					+ (titulo.getValue()!=null ? titulo.getValue() : "") 
					+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
			displayText.setValue(str);
		}
	}
	
	private void buildListener() {
		
		ChangeListener idListener =  super.getListenerRepository().get("displayText");
		if(idListener==null){
			idListener = new ChangeListener<Long>(){
		
				public void changed(ObservableValue arg0, Long arg1, Long arg2) {
					String str = (arg2==null ? "" : "(ID: "+arg2.toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
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
							+ (arg2!=null ? arg2 : "") 
							+ (data.getValue()!=null ? " em "+formataDataHora(data.getValue()) : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText1", tituloListener);
		}else
			titulo.removeListener(tituloListener);
		
		titulo.addListener(tituloListener);
		
		ChangeListener<Date> dataListener = super.getListenerRepository().get("displayText2");
		if(dataListener==null){
			dataListener = new ChangeListener<Date>(){
				@Override
				public void changed(ObservableValue arg0, Date arg1, Date arg2) {
					String str = (id.getValue()==null ? "" : "(ID: "+id.getValue().toString()+") " ) 
							+ (titulo.getValue()!=null ? titulo.getValue() : "") 
							+ (arg2!=null ? " em "+formataDataHora(arg2) : "");
					displayText.setValue(str);
				}
				
			};
			super.addListener("displayText2", dataListener);
		}else
			data.removeListener(dataListener);
		
		data.addListener(dataListener);
		
	}
	
	private String formataDataHora(Date data){
		String pattern = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(data);
	}
	
	@Override
	public void removeAllListener() {
		super.removeAllListener();
	}
	
	public AcaoFindModelView() {
		super(new Acao());
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

	
	
	public AcaoFindModelView getNewInstance(){
		return new AcaoFindModelView(new Acao());
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
	 * @return the data
	 */
	public SimpleObjectProperty<Date> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(SimpleObjectProperty<Date> data) {
		this.data = data;
	}

	/**
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
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
