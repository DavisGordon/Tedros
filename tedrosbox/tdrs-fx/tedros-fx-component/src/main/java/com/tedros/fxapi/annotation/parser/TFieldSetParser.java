package com.tedros.fxapi.annotation.parser;

import com.tedros.fxapi.annotation.layout.TFieldSet;
import com.tedros.fxapi.descriptor.TComponentDescriptor;
import com.tedros.fxapi.form.TComponentBuilder;
import com.tedros.fxapi.form.TFieldBox;

import javafx.scene.Node;


/**
 * <pre>
 * The {@link TFieldSet} annotation parser, this parser will read the values 
 * in the annotation and set them at the {@link com.tedros.fxapi.layout.TFieldSet} component.
 * </pre>
 * @author Davis Gordon
 * */
public class TFieldSetParser extends TAnnotationParser<TFieldSet, com.tedros.fxapi.layout.TFieldSet> {

	private static TFieldSetParser instance;
	
	private TFieldSetParser(){
		
	}
	
	public static  TFieldSetParser getInstance(){
		if(instance==null)
			instance = new TFieldSetParser();
		return instance;	
	}
	
	@Override
	public void parse(TFieldSet annotation, com.tedros.fxapi.layout.TFieldSet object, String... byPass) throws Exception {
		
		object.tAddLegend(iEngine.getString(annotation.legend()));
		TFieldBox firstItem = (TFieldBox) object.getUserData();
		for(String field: annotation.fields()){
			Node node = null;
			if(firstItem!=null && field.equals(firstItem.gettControlFieldName())){
				node = firstItem;
			}else{
				final TComponentDescriptor descriptor = new TComponentDescriptor(getComponentDescriptor(), field);
				node = TComponentBuilder.getField(descriptor);
			}
			if(node!=null){
				object.tAddContent(node);
			}
		}
		
		super.parse(annotation, object, "layoutType", "fields", "legend", "skipAnnotatedField", "mode" );
	}
}
