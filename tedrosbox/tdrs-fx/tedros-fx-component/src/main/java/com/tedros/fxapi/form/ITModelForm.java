package com.tedros.fxapi.form;

import java.util.List;

import com.tedros.core.model.ITModelView;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;

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
