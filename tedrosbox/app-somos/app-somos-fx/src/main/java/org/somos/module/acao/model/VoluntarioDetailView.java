/**
 * 
 */
package org.somos.module.acao.model;

import org.somos.model.Pessoa;
import org.somos.model.TipoAjuda;
import org.somos.model.Voluntario;
import org.somos.module.pessoa.model.PessoaFindModelView;
import org.somos.module.pessoa.table.PessoaCallback;
import org.somos.module.tipoAjuda.model.TipoAjudaFindModelView;
import org.somos.module.tipoAjuda.model.TipoAjudaModelView;

import com.tedros.fxapi.annotation.control.TCallbackFactory;
import com.tedros.fxapi.annotation.control.TCellFactory;
import com.tedros.fxapi.annotation.control.TLabel;
import com.tedros.fxapi.annotation.control.TModelViewType;
import com.tedros.fxapi.annotation.control.TMultipleSelectionModal;
import com.tedros.fxapi.annotation.control.TOneSelectionModal;
import com.tedros.fxapi.annotation.control.TTableColumn;
import com.tedros.fxapi.annotation.control.TTableView;
import com.tedros.fxapi.annotation.form.TForm;
import com.tedros.fxapi.annotation.layout.THBox;
import com.tedros.fxapi.annotation.layout.THGrow;
import com.tedros.fxapi.annotation.layout.TPane;
import com.tedros.fxapi.annotation.layout.TPriority;
import com.tedros.fxapi.annotation.presenter.TBehavior;
import com.tedros.fxapi.annotation.presenter.TDecorator;
import com.tedros.fxapi.annotation.presenter.TDetailTableViewPresenter;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.annotation.reader.TColumnReader;
import com.tedros.fxapi.annotation.reader.TFormReaderHtml;
import com.tedros.fxapi.annotation.reader.TReaderHtml;
import com.tedros.fxapi.annotation.reader.TTableReaderHtml;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TDetailFieldDecorator;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.scene.layout.Priority;

/**
 * @author Davis Gordon
 *
 */
@TFormReaderHtml
@TForm(name = "Voluntário inscrito", showBreadcrumBar=false)
@TDetailTableViewPresenter(
		presenter=@TPresenter(behavior=@TBehavior(type=TDetailFieldBehavior.class),
				decorator = @TDecorator(type=TDetailFieldDecorator.class, viewTitle="Voluntários")
				),
		tableView=@TTableView(editable=true, control=@TControl(prefHeight=180,parse = true),
			columns = { @TTableColumn(cellValue="pessoa", text = "Nome", resizable=true,
							cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=PessoaCallback.class))), 
						@TTableColumn(cellValue="pessoa", text = "Profissão", resizable=true,
							cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=PessoaCallback.class))), 
						@TTableColumn(cellValue="pessoa", text = "Contato", resizable=true,
							cellFactory=@TCellFactory(parse = true, callBack=@TCallbackFactory(parse=true, value=PessoaCallback.class))), 
						@TTableColumn(cellValue="tiposAjudaDesc", text = "Tipos de ajuda", resizable=true)
				}))
public class VoluntarioDetailView extends TEntityModelView<Voluntario> {

	private SimpleLongProperty id;
	
	@TReaderHtml
	@TLabel(text="Pessoa")
	@TOneSelectionModal(modelClass = Pessoa.class, modelViewClass = PessoaFindModelView.class,
	width=300, height=50, required=true)
	@THBox(	pane=@TPane(children={"pessoa","tiposAjuda"}), spacing=10, fillHeight=true,
	hgrow=@THGrow(priority={@TPriority(field="pessoa", priority=Priority.NEVER), 
   				   		@TPriority(field="tiposAjuda", priority=Priority.NEVER)}))
	private SimpleObjectProperty<Pessoa> pessoa;	
	
	
	@TLabel(text="Tipos de Ajuda")
	@TTableReaderHtml(label=@TLabel(text="Tipo de Ajuda:"), 
	column = { 	@TColumnReader(field = "descricao", name = "Descricao"), 
				@TColumnReader(field = "tipoPessoa", name = "Tipo pessoa")})
	@TMultipleSelectionModal(modelClass = TipoAjuda.class, modelViewClass = TipoAjudaFindModelView.class, width=350, height=50, required=true)
	@TModelViewType(modelClass=TipoAjuda.class, modelViewClass=TipoAjudaModelView.class, required=true)
	private ITObservableList<TipoAjudaModelView> tiposAjuda;
	
	
	private SimpleStringProperty tiposAjudaDesc;
	
	public VoluntarioDetailView(Voluntario entity) {
		super(entity);
		if(tiposAjudaDesc==null) 
			tiposAjudaDesc = new SimpleStringProperty();
		super.registerProperty("tiposAjudaDesc", tiposAjudaDesc);
		
		ListChangeListener<TipoAjudaModelView> ltn = (l) ->{
			readTiposAjuda(l.getList());
		};
		super.addListener("tiposAjuda", ltn);
		tiposAjuda.addListener(new WeakListChangeListener<>(ltn));
		readTiposAjuda(null);
	}
	
	private void readTiposAjuda(ObservableList<? extends TipoAjudaModelView> list) {
		String s = "";
		if(list!=null) 
			for(TipoAjudaModelView e : list)
				s += ("".equals(s)) ? e.getDescricao().getValue() : ", "+e.getDescricao().getValue();
		else if(super.getEntity().getTiposAjuda()!=null) {
			for(TipoAjuda e : super.getEntity().getTiposAjuda())
				s += ("".equals(s)) ? e.getDescricao() : ", "+e.getDescricao();
		}
				
		tiposAjudaDesc.setValue(s);
		
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return null;
	}

	/**
	 * @return the pessoa
	 */
	public SimpleObjectProperty<Pessoa> getPessoa() {
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(SimpleObjectProperty<Pessoa> pessoa) {
		this.pessoa = pessoa;
	}

	

	
	/**
	 * @return the tiposAjuda
	 */
	public ITObservableList<TipoAjudaModelView> getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(ITObservableList<TipoAjudaModelView> tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
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
	 * @return the tiposAjudaDesc
	 */
	public SimpleStringProperty getTiposAjudaDesc() {
		return tiposAjudaDesc;
	}

	/**
	 * @param tiposAjudaDesc the tiposAjudaDesc to set
	 */
	public void setTiposAjudaDesc(SimpleStringProperty tiposAjudaDesc) {
		this.tiposAjudaDesc = tiposAjudaDesc;
	}

}
