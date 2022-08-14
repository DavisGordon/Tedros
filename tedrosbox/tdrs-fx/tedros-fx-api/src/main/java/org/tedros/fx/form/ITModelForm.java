package org.tedros.fx.form;

import java.util.List;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.descriptor.TFieldDescriptor;
import org.tedros.fx.domain.TViewMode;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.web.WebView;

@SuppressWarnings("rawtypes")
public interface ITModelForm<M extends ITModelView> extends ITForm {
		
	public void tAddFormItem(Node fieldBox);
	
	public TViewMode gettMode();
	
	public M gettModelView();
	
	public abstract void tInitializeReader();
	
	public void settReaderMode();
	
	public void settEditMode();
	
	public List<TFieldDescriptor> gettFieldDescriptorList();
	
	public void tAddAssociatedObject(final String name, final Object obj);
	
	public Object gettAssociatedObject(final String name);
	
	public ObservableList<Node> gettFormItens();
	
	public WebView gettWebView();
}
