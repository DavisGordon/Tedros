package org.tedros.api.form;

import java.util.List;

import org.tedros.api.descriptor.ITFieldDescriptor;
import org.tedros.api.presenter.view.TViewMode;
import org.tedros.core.model.ITModelView;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.web.WebView;

@SuppressWarnings("rawtypes")
public interface ITModelForm<M extends ITModelView> extends ITForm {
	
	void tAddFormItem(Node fieldBox);
	
	TViewMode gettMode();
	
	M gettModelView();
	
	abstract void tInitializeReader();
	
	void settReaderMode();
	
	void settEditMode();
	
	List<ITFieldDescriptor> gettFieldDescriptorList();
	
	void tAddAssociatedObject(final String name, final Object obj);
	
	Object gettAssociatedObject(final String name);
	
	ObservableList<Node> gettFormItens();
	
	WebView gettWebView();
}
