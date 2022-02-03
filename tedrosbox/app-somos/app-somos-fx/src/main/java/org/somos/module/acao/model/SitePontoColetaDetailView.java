/**
 * 
 */
package org.somos.module.acao.model;

import org.somos.model.SitePontoColeta;

import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TNumberSpinnerField;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.control.TTextAreaField;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Pontos de coleta", showBreadcrumBar=false)
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Pontos de coleta")
				),
		tableView=@TTableView(editable=true, control=@TControl(prefHeight=180,parse = true),
			columns = { @TTableColumn(cellValue="descricao", text = "Descrição", resizable=true),
			 @TTableColumn(cellValue="ordem", text = "Ordenação", resizable=true)
			}))
public class SitePontoColetaDetailView extends TEntityModelView<SitePontoColeta> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="Descricão")
	@TTextAreaField(required=true, maxLength=300, wrapText=true,
	control=@TControl(maxWidth=300,prefHeight=100, parse = true))
	@THBox(	pane=@TPane(children={"descricao","ordem"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="descricao", priority=Priority.NEVER), 
   				   		@TPriority(field="ordem", priority=Priority.NEVER) }))
	private SimpleStringProperty descricao;
	
	@TReaderHtml
	@TLabel(text="Ordem")
	@TNumberSpinnerField(maxValue = 100)
	private SimpleIntegerProperty ordem;

	public SitePontoColetaDetailView(SitePontoColeta entity) {
		super(entity);
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return descricao;
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public SimpleStringProperty getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the ordem
	 */
	public SimpleIntegerProperty getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(SimpleIntegerProperty ordem) {
		this.ordem = ordem;
	}

}
