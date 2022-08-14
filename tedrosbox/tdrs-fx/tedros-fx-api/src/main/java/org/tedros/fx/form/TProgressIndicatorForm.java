/**
 * 
 */
package org.tedros.fx.form;

import java.util.List;
import java.util.Map;

import org.tedros.core.control.TProgressIndicator;
import org.tedros.core.model.ITModelView;
import org.tedros.core.module.TObjectRepository;
import org.tedros.core.presenter.ITPresenter;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TViewMode;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * @author Davis Gordon
 *
 */
public class TProgressIndicatorForm<M extends ITModelView<?>> extends StackPane implements ITModelForm<M> {

	private final ITModelForm<M> form;
	private final TProgressIndicator pIndicator = new TProgressIndicator(this);
	private final ChangeListener<Boolean> loadedListener = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean n) {
			if(n) {
				getChildren().clear();
				getChildren().add((Node) form);
			}
		}
	};
	
	public TProgressIndicatorForm(ITModelForm<M> form) {
		this.form = form;
		if(form.isLoaded())
			getChildren().add((Node) form);
		else {
			this.form.tLoadedProperty().addListener(new WeakChangeListener<>(loadedListener));
			BooleanBinding bb = BooleanBinding.booleanExpression(this.form.tLoadedProperty()).not();
			this.pIndicator.bind(bb);
		}
		autosize();
		setAlignment(Pos.TOP_LEFT);
		setPadding(new Insets(0));
	}
	
	@Override
	public String toString() {
		return form.gettModelView().getClass().getSimpleName();
	}
	
	public ITModelForm<M> gettForm() {
		return this.form;
	}
	
	@Override
	public void tInitializeForm() {
		this.form.tInitializeForm();
	}

	@Override
	public void tReloadForm() {
		this.form.tReloadForm();
	}

	@Override
	public TFieldBox gettFieldBox(String fieldName) {
		return this.form.gettFieldBox(fieldName);
	}

	@Override
	public Map<String, TFieldBox> gettFieldBoxMap() {
		return this.form.gettFieldBoxMap();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void settPresenter(ITPresenter presenter) {
		this.form.settPresenter(presenter);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ITPresenter gettPresenter() {
		return this.form.gettPresenter();
	}

	@Override
	public TObjectRepository gettObjectRepository() {
		return this.form.gettObjectRepository();
	}

	@Override
	public void tDispose() {
		this.form.tDispose();
	}

	@Override
	public ReadOnlyBooleanProperty tLoadedProperty() {
		return this.form.tLoadedProperty();
	}

	@Override
	public boolean isLoaded() {
		return this.form.isLoaded();
	}

	@Override
	public void tAddFormItem(Node fieldBox) {
		this.form.tAddFormItem(fieldBox);
	}

	@Override
	public TViewMode gettMode() {
		return this.form.gettMode();
	}

	@Override
	public M gettModelView() {
		return this.form.gettModelView();
	}

	@Override
	public void tInitializeReader() {
		this.form.tInitializeReader();
	}

	@Override
	public void settReaderMode() {
		this.form.settReaderMode();
	}

	@Override
	public void settEditMode() {
		this.form.settEditMode();
	}

	@Override
	public List<TFieldDescriptor> gettFieldDescriptorList() {
		return this.form.gettFieldDescriptorList();
	}

	@Override
	public void tAddAssociatedObject(String name, Object obj) {
		this.form.tAddAssociatedObject(name, obj);
	}

	@Override
	public Object gettAssociatedObject(String name) {
		return this.form.gettAssociatedObject(name);
	}

	@Override
	public ObservableList<Node> gettFormItens() {
		return this.form.gettFormItens();
	}

	@Override
	public WebView gettWebView() {
		return this.form.gettWebView();
	}
	

	@Override
	public TSetting gettSetting(){
		return this.form.gettSetting();
	}
	

}
