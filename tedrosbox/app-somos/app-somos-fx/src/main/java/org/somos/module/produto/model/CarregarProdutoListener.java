/**
 * 
 */
package org.somos.module.produto.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.somos.model.Produto;

import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.fxapi.annotation.listener.TChangeListener;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.modal.TMessage;
import com.tedros.fxapi.modal.TMessageType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewSimpleBaseBehavior;
import com.tedros.fxapi.process.TEntityProcess;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.TextField;

/**
 * @author Davis Gordon
 *
 */
public class CarregarProdutoListener extends TChangeListener<Boolean> {

	@SuppressWarnings("unchecked")
	@Override
	public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean val) {
		TFieldBox codBox = super.getComponentDescriptor().getForm().gettFieldBox("codigo");
		SimpleObjectProperty<Produto> prop = (SimpleObjectProperty<Produto>) super.getComponentDescriptor().getModelView().getProperty("produto");
		String propVal = prop!=null && prop.getValue()!=null ? prop.getValue().getCodigo() : "";
		String cod = ((TextField)codBox.gettControl()).getText();
		if(!val && StringUtils.isNotBlank(cod) && !cod.equals(propVal)) {
			Produto ex = new Produto();
			ex.setCodigo(cod);
			TFieldBox prodBox =  super.getComponentDescriptor().getForm().gettFieldBox("produto");
			TSelectionModal modal = (TSelectionModal) prodBox.gettControl();
			final TDynaPresenter presenter = (TDynaPresenter) super.getComponentDescriptor().getForm().gettPresenter();
			TEntityProcess process = new TEntityProcess(Produto.class, "IProdutoControllerRemote") {};
			process.find(ex);
			process.stateProperty().addListener(new ChangeListener<State>() {
				@SuppressWarnings("unchecked")
				@Override
				public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
					if(arg2.equals(State.SUCCEEDED)){
						List<TResult<Produto>> resultados = (List<TResult<Produto>>) process.getValue();
						if(resultados.isEmpty())
							return;
						TResult result = resultados.get(0);
						if(result.getResult().equals(EnumResult.ERROR)) {
							System.out.println(result.getMessage());
							((TDynaViewSimpleBaseBehavior)presenter.getBehavior()).addMessage(new TMessage(TMessageType.ERROR, result.getMessage()));
						}else{
							Produto entity = (Produto) result.getValue();
							modal.gettSelectedItems().clear();
							if(entity!=null) {
								modal.gettSelectedItems().add(new ProdutoFindModelView(entity));
							}else {
								((TDynaViewSimpleBaseBehavior)presenter.getBehavior()).addMessage(new TMessage(TMessageType.WARNING, "Produto não encontrado para o codigo informado!"));
							}
						}
					}
				}
			});
			process.startProcess();
		}
	}

}
