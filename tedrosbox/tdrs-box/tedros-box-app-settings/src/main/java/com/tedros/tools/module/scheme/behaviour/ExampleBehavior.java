package com.tedros.tools.module.scheme.behaviour;

import java.util.Map;

import com.tedros.fxapi.control.TCheckBoxField;
import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TVerticalRadioGroup;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.domain.TViewMode;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.layout.TFieldSet;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.reader.TTextReader;
import com.tedros.tools.module.scheme.decorator.ExampleDecorator;
import com.tedros.tools.module.scheme.model.Example;
import com.tedros.tools.module.scheme.model.ExampleMV;
import com.tedros.util.TColorUtil;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class ExampleBehavior extends TDynaViewCrudBaseBehavior<ExampleMV, Example> {

	@Override
	public void load() {
		super.load();
		configModesRadio();
		
		addAction(new TPresenterAction(TActionType.NEW) {

			@Override
			public boolean runBefore() {
				ExampleMV model = new ExampleMV();
				setModelView(model);
				showForm(TViewMode.EDIT);
				return false;
			}

			@Override
			public void runAfter() {
				
			}
		});
		
		newAction();
	}
	
	private ExampleDecorator getDecorator(){
		return (ExampleDecorator) getPresenter().getDecorator();
	}
	
	@SuppressWarnings("unchecked")
	public void processPanelTextStyle(String color, String size) {
		
		StringBuilder style = new StringBuilder("-fx-text-fill: "+color+";");
		style.append("-fx-font-size: "+size+"em; ");
		style.append("-fx-font: Arial; -fx-font-weight: bold; -fx-font-smoothing-type:lcd; -fx-padding: 5 5 5 5; ");
		
		ExampleDecorator decorator = getDecorator();
		decorator.gettEditModeRadio().setStyle(style.toString());
		decorator.gettReadModeRadio().setStyle(style.toString());
		((TDynaView<ExampleMV>) decorator.getView()).gettViewTitle().setStyle(style.toString());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processFieldSetBorderAndTextColor(String color, String size){
		final ITModelForm<ExampleMV> form = getForm();
		Node node = ((TDefaultForm<ExampleMV>) ((TProgressIndicatorForm) form).gettForm()).lookup("#stringField-fieldSet");
		if(node instanceof TFieldSet){
			TFieldSet fieldSet = (TFieldSet) node;
			
			StringBuilder borderCss = new StringBuilder("-fx-border-color: "+color+";");
			borderCss.append("-fx-border-width: 2px;");
			borderCss.append("-fx-border-style: solid;");
			borderCss.append("-fx-background-color: transparent;");
			
			fieldSet.gettContentPane().setStyle(borderCss.toString());
			
			StringBuilder textCss = new StringBuilder("-fx-text-fill: "+color+";");
			textCss.append("-fx-font-size: "+size+"em; -fx-font-weight: bold; ");
			
			fieldSet.gettLegendLabel().setStyle(textCss.toString());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processFieldSetLegendBackgroundColor(Color panelColor){
		final ITModelForm<ExampleMV> form = getForm();
		Node node = ((TDefaultForm<ExampleMV>) ((TProgressIndicatorForm) form).gettForm()).lookup("#stringField-fieldSet");
		if(node instanceof TFieldSet){
			
			java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(panelColor));
			String r = String.valueOf(rgb.getRed());
			String g = String.valueOf(rgb.getGreen());
			String b = String.valueOf(rgb.getBlue());
			
			
			TFieldSet fieldSet = (TFieldSet) node;
			StringBuilder strBuilder = new StringBuilder("-fx-background-color: rgba("+r+","+g+","+b+",1); ");
			strBuilder.append("-fx-padding: 0 0 5 0;");
			fieldSet.gettLegendBox().setStyle(strBuilder.toString());
		}
	}
	
	
	public void processPanelStyle(Color panelColor, String opacity){
		
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(panelColor));
		String r = String.valueOf(rgb.getRed());
		String g = String.valueOf(rgb.getGreen());
		String b = String.valueOf(rgb.getBlue());
		
		String tHeaderBox = "-fx-background-color: rgba("+r+","+g+","+b+","+opacity.replaceAll(",", ".")+"); "+
							"-fx-background-size: cover; "+
							"-fx-border-color: transparent transparent lightslategrey  transparent; "+
							"-fx-border-width: 0.9; "+
						    "-fx-border-insets: 0, 0 0 1 0;";
		
		String tViewTitleBox = 	"-fx-border-color: transparent;" +
								"-fx-background-color: rgba("+r+","+g+","+b+","+opacity.replaceAll(",", ".")+");";
		
		String tPane = 	"-fx-background-color: rgba("+r+","+g+","+b+","+opacity.replaceAll(",", ".")+");"+
						"-fx-padding: 20 20 20 20;"+
						"-fx-spacing: 8;";
		
		TDynaView<ExampleMV> view = getView();
		
		view.gettHeaderHorizontalLayout().setStyle(tHeaderBox);
		view.gettViewTitleLayout().setStyle(tViewTitleBox);
		view.gettFormSpace().setStyle(tPane);
		//view.getModelListBox().setStyle(tPane);
		
	}
	
	public void processPanelButtons(Color buttonTextColor, Color buttonColor, Color buttonBorderColor, String size, String opacity ){
		
		String textColor = TColorUtil.toHexadecimal(buttonTextColor);
		String borderColor = TColorUtil.toHexadecimal(buttonBorderColor);
		
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(buttonColor));
		String r = String.valueOf(rgb.getRed());
		String g = String.valueOf(rgb.getGreen());
		String b = String.valueOf(rgb.getBlue());
		
		String tButton = "-fx-background-color: rgba("+r+","+g+","+b+","+opacity.replaceAll(",", ".")+"); "+
							"-fx-text-fill: "+textColor+";"+	
							"-fx-font-size: "+ size +"em; "+
							"-fx-background-radius: 0;"+
							"-fx-background-insets: 0;"+
							"-fx-padding: 3px 6px 4px 6px;"+
							"-fx-border-color: transparent transparent transparent "+borderColor+", transparent transparent transparent transparent;";
		
		String tLastButton = "-fx-background-color: rgba("+r+","+g+","+b+","+opacity.replaceAll(",", ".")+"); "+
				"-fx-text-fill: "+textColor+";"+	
				"-fx-font-size: "+ size +"em; "+
				"-fx-background-radius: 0;"+
				"-fx-background-insets: 0;"+
				"-fx-padding: 3px 6px 4px 6px;"+
				"-fx-border-color: transparent "+borderColor+" transparent "+borderColor+" , transparent "+borderColor+" transparent transparent;";
		
		
		ExampleDecorator decorator = getDecorator();
		decorator.gettNewButton().setStyle(tButton);
		decorator.gettSaveButton().setStyle(tButton);
		decorator.gettDeleteButton().setStyle(tLastButton);
		
	}
	
	
	
	/**/
	
	public void processLabelStyle(Color textColor, String size) {
		
		final Map<String, TFieldBox> fieldBoxMap = getForm().gettFieldBoxMap();
		
		for(String key : fieldBoxMap.keySet()){
			TFieldBox fieldBox = fieldBoxMap.get(key);
			final Node label = fieldBox.gettLabel(); 
			if(label!=null){
				label.setStyle("-fx-text-fill: "+TColorUtil.toHexadecimal(textColor)+";"+ 
						"-fx-font: \"Helvetica\";"+
						"-fx-font-size: "+size+"em;");
			}
		}
	}
	
	public void processTextStyle(Color textColor, String size) {
		
		final Map<String, TFieldBox> fieldBoxMap = getForm().gettFieldBoxMap();
		
		for(String key : fieldBoxMap.keySet()){
			TFieldBox fieldBox = fieldBoxMap.get(key);
			final Node node = fieldBox.gettControl();
			if(node!=null && node instanceof TTextReader){
				node.setStyle("-fx-fill: "+TColorUtil.toHexadecimal(textColor)+";"+ 
						"-fx-font: \"Helvetica\";"+
						"-fx-font-size: "+size+"em;"+
						"-fx-font-weight: bold;");
			}
		}
	}
		
	public void processEditInputStyle(String inputLabelSize, String inputTextSize, 
			Color inputColor, Color inputBorderColor, Color inputLabelColor, Color inputTextColor, boolean bold) {
		
		// seta a cor de fundo
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(inputColor));
		String r = String.valueOf(rgb.getRed());
		String g = String.valueOf(rgb.getGreen());
		String b = String.valueOf(rgb.getBlue());
		
		String borderHexColor = TColorUtil.toHexadecimal(inputBorderColor);
		
		final Map<String, TFieldBox> fieldBoxMap = getForm().gettFieldBoxMap();
		
		for(String key : fieldBoxMap.keySet()){
			TFieldBox fieldBox = fieldBoxMap.get(key);
			
			final Node node = fieldBox.gettControl(); 
			if(node!=null){
				fieldBox.settFieldStyle("-fx-text-fill: "+TColorUtil.toHexadecimal( 
						isChoiceField(fieldBox) ? inputLabelColor : inputTextColor)+"; "+ 
						(!isChoiceField(fieldBox)
								? "-fx-background-color: rgba("+r+","+g+","+b+", 1); " 
										: "")+
						"-fx-font-size: "+  inputTextSize +"em; "+
						(!isChoiceField(fieldBox) 
								?  "-fx-border-color: "+borderHexColor+" "+borderHexColor+" "+borderHexColor+" "+borderHexColor+"; " 
										: "")+
						(bold ? "-fx-font-weight: bold; " : "") );
			}
		}
	}
	
	public void processBackgroundColor(String opacity, Color backgroundColor) {
		
		java.awt.Color rgb = TColorUtil.hex2Rgb(TColorUtil.toHexadecimal(backgroundColor));
		String r = String.valueOf(rgb.getRed());
		String g = String.valueOf(rgb.getGreen());
		String b = String.valueOf(rgb.getBlue());
		
		applyFormStyle(opacity, r, g, b);
	}
	
	private void applyFormStyle(String value, String r, String g, String b) {
		((Node)getForm()).setStyle("-fx-background-color: rgba("+r+","+g+","+b+","+value.replaceAll(",", ".")+");");
	}
	
	private boolean isChoiceField(TFieldBox fieldBox) {
		return (fieldBox.gettControl() instanceof TVerticalRadioGroup || fieldBox.gettControl() instanceof THorizontalRadioGroup 
				|| fieldBox.gettControl() instanceof TCheckBoxField);
	}
	
	
	
	
	@Override
	public void colapseAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean processNewEntityBeforeBuildForm(ExampleMV model) {
		return true;
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	

	

}
