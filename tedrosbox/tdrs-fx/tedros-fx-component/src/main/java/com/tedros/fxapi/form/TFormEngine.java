/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 11/11/2013
 */
package com.tedros.fxapi.form;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.model.ITModelView;
import com.tedros.core.module.TObjectRepository;
import com.tedros.fxapi.annotation.TDebugConfig;
import com.tedros.fxapi.builder.ITReaderHtmlBuilder;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.descriptor.TFieldDescriptor;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.reader.THtmlReader;
import com.tedros.fxapi.util.TReflectionUtil;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TFormEngine<M extends ITModelView<?>, F extends ITModelForm<M>>  {
	
	private TViewMode mode;
	private TSetting setting;
	private TModelViewLoader<M> modelViewLoader;
	private final M modelView;
	private final F form;
	private final Map<String, Object> associatedObjectsMap;
	private ObservableList<Node> fields;
	private WebView webView;
	private SimpleBooleanProperty loaded = new SimpleBooleanProperty(false);
	private TObjectRepository tObjectRepository = new TObjectRepository();
	private final TTriggerLoader<M, ITModelForm<M>> triggerLoader;
	private ChangeListener<Boolean> chl = (ob, o, n) -> {
		if(n && mode!=null) { 
			buildTriggers();
			runSetting();
		}
	};
	
	public TFormEngine(final F form, final M modelView) {
		this.form = form;
		if(StringUtils.isBlank(this.form.getId()))
			this.form.setId("t-form");
		this.modelView = modelView;
		this.associatedObjectsMap = new HashMap<>(0);
		triggerLoader = new TTriggerLoader<M, ITModelForm<M>>(form);
		loadedProperty().addListener(new WeakChangeListener<>(chl));
	}
	
	public TFormEngine(final F form, final M modelView, boolean readerMode) {
		this.form = form;
		this.modelView = modelView;
		this.associatedObjectsMap = new HashMap<>(0);
		triggerLoader = new TTriggerLoader<M, ITModelForm<M>>(form);
		loadedProperty().addListener(new WeakChangeListener<>(chl));
		if(readerMode)
			setReaderMode();
		else
			setEditMode();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setReaderMode(){
		
		if(TDebugConfig.detailParseExecution)
			System.out.println("[TFormEngine][ReadeMode][initialized]");
		
		resetForm();
		mode = TViewMode.READER;
		buildModelViewLoader();
		
		try {
			if(StringUtils.isBlank(this.form.getId()))
				form.setId("t-reader");
				int totalHtmlReaders = 0;
				fields = this.modelViewLoader.getReaders();
				
				for (Node node : fields) {
					if(node instanceof THtmlReader)
						totalHtmlReaders++;		
				}
				
				if(fields.size() == totalHtmlReaders){
					
					StringBuffer sbf = new StringBuffer();
					for (Node node : fields){
						THtmlReader htmlReader = (THtmlReader) node;
						sbf.append(htmlReader.getText()).append("\n");
					}
					StringBuffer op = new StringBuffer("<html><body>");
					StringBuffer cl = new StringBuffer("</body></html>");
					sbf = new StringBuffer(op.toString()+sbf.toString()+cl.toString());
					//System.out.println(sbf.toString());
					webView = new WebView();
					form.tAddFormItem(webView);
					webView.setDisable(false);
					
					ChangeListener<Number> hListener = (arg0, arg1, arg2) -> webView.setPrefHeight((double) arg2);
					this.tObjectRepository.add("webviewformchl", hListener);
					
					((Pane)this.form).heightProperty().addListener(new WeakChangeListener(hListener));
										
					if(modelView.getClass().getAnnotations()!=null){
						Object[] arrReaderHtml = TReflectionUtil.getReaderHtmlBuilder(Arrays.asList(modelView.getClass().getAnnotations()));	
						if(arrReaderHtml !=null ){
							Annotation readerAnnotation = (Annotation) arrReaderHtml[0];
							ITReaderHtmlBuilder readerBuilder = (ITReaderHtmlBuilder) arrReaderHtml[1];
							webView.getEngine().loadContent(readerBuilder.build(readerAnnotation, sbf).getText());
						}else
							webView.getEngine().loadContent(sbf.toString());
					}else
						webView.getEngine().loadContent(sbf.toString());
					
				}else{
					StringBuffer sbf = null;
					for (Node node : fields){
						if(node instanceof THtmlReader){
							if(webView==null){
								sbf = new StringBuffer();
								webView = new WebView();
								form.tAddFormItem(webView);
							}
							THtmlReader htmlReader = (THtmlReader) node;
							sbf.append(htmlReader.getText()).append("\n");
						}else						
							form.tAddFormItem(node);
					}
					
					if(sbf!=null)
						webView.getEngine().loadContent(sbf.toString());
				}
			loaded.setValue(true);
			initializeReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildModelViewLoader() {
		this.modelViewLoader = new TModelViewLoader<M>(modelView, this.form);
	}

	public void setEditMode(){
		if(TDebugConfig.detailParseExecution)
			System.out.println("[TFormEngine][EditMode][initialized]");
		
		resetForm();
		mode = TViewMode.EDIT;
		buildModelViewLoader();
		this.loaded.bind(this.modelViewLoader.allLoadedProperty());
		Thread taskThread = new Thread(()-> {
			Platform.runLater(()-> {
				try {
					if(StringUtils.isBlank(this.form.getId()))
						this.form.setId("t-form");
					this.modelViewLoader.loadEditFields(form.getChildren());
					
					initializeForm();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		taskThread.setDaemon(true);
		taskThread.start();
	}
	
	public ReadOnlyBooleanProperty loadedProperty() {
		return loaded;
	}
	
	public void setLoaded(boolean loaded) {
		this.loaded.setValue(loaded);
	}
	
	public void reloadForm(){
		if(mode.equals(TViewMode.EDIT))
			setEditMode();
		if(mode.equals(TViewMode.READER))
			setReaderMode();
		buildTriggers();
	}
	
	public TFieldBox getFieldBox(String fieldName){
		if(modelViewLoader!=null)
			return (TFieldBox) modelViewLoader.geFieldBox(fieldName);
		return null;
		
	}
	
	public TViewMode getMode(){
		return mode;
	}
	
	
	public Map<String, TFieldBox> getFieldBoxMap() {
		return (modelViewLoader!=null) ? modelViewLoader.getFieldBoxMap(): null;
	}
	
	public M getModelView() {
		return modelView;
	}
	
	public void initializeForm(){
		form.tInitializeForm();
	}; 
	
	public void initializeReader(){
		form.tInitializeReader();
	}
	
	public List<TFieldDescriptor> getFieldDescriptorList(){
		return modelViewLoader.getFieldDescriptorList();
	}
		
	public void addAssociatedObject(final String name, final Object obj) {
		this.associatedObjectsMap.put(name, obj);
	}
	
	public Object getAssociatedObject(String name) {
		return (this.associatedObjectsMap.containsKey(name)) ? this.associatedObjectsMap.get(name) : null;
	}

	public void resetForm() {
		this.mode = null;
		this.tObjectRepository.clear();
		this.loaded.unbind();
		this.loaded.setValue(false);
		this.modelViewLoader = null;
		if(form.getChildren()!=null){
			try{
				form.getChildren().clear();
			}catch(IllegalArgumentException e){
				// bug
				form.getChildren().clear();
			}
		}
		
		fields = null;
		webView = null;
	}
	
	public WebView getWebView() {
		return webView;
	}

	/**
	 * @return the fields
	 */
	public ObservableList<Node> getFields() {
		return mode.equals(TViewMode.READER) 
				? fields :
					this.form.getChildren();
	}

	/**
	 * @return the tObjectRepository
	 */
	public TObjectRepository getObjectRepository() {
		return tObjectRepository;
	}
	
	private void runSetting() {
		if(this.mode.equals(TViewMode.READER))
			return; 
		
		com.tedros.fxapi.annotation.form.TSetting a = this.modelView.getClass().getAnnotation(com.tedros.fxapi.annotation.form.TSetting.class);
		if(a!=null) {
			Class<? extends TSetting> c = a.value();
			try {
				setting = c.getConstructor(TComponentDescriptor.class).newInstance(this.modelViewLoader.getDescriptor());
				setting.run();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			
		
		}
	}
	
	private void buildTriggers() {
		try {
			triggerLoader.buildTriggers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the setting
	 */
	public TSetting getSetting() {
		return setting;
	}
	
}
