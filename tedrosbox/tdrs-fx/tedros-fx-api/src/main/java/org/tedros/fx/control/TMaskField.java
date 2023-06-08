/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/11/2013
 */
package org.tedros.fx.control;

import org.tedros.fx.util.TMaskUtil;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * <pre>
 * A mask input control.
 * 
 * Apply a mask for this input control.
 * 
 * A = Only alpha characters [A - Z]
 * # = Any character, alfa, numeric or symbol
 * 9 = Only numeric characters [0-9]
 * 
 * Example:
 * 
 * 999.999.999-99 
 * 99.999.999/9999-99
 * AAA-9999 
 * (99) 9999-99999
 * [###] A999
 * </pre>
 * 
 * @author Davis Gordon
 * */ 
public class TMaskField extends TRequiredTextField {
	
	private SimpleIntegerProperty maxLenght;
	private SimpleStringProperty maskProperty ;
	private ChangeListener<String> listener;

	public TMaskField() {
		init();
	}
	public TMaskField(String mask) {
		init();
		setMask(mask);
	}

	private void init() {
		maskProperty = new SimpleStringProperty();
		listener =	new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					String arg1, String arg2) {
				if(maskProperty.getValue()!=null) {
					textProperty().removeListener(this);
					setText(TMaskUtil.applyMask(getText(), maskProperty.get()));
					textProperty().addListener(this);
				}
			}
		};
		
		textProperty().addListener(listener);
		super.tRequiredNodeProperty().setValue(this);
	}
	
	/**
	* <pre>
	* Sets the value of the property mask.
	* 
	* Property description:
	* 
	* Apply a mask for this input control.
	* 
	* A = Only alpha characters [A - Z]
	* # = Any character, alfa, numeric or symbol
	* 9 = Only numeric characters [0-9]
	* 
	* Example:
	* 
	* 999.999.999-99 
	* 99.999.999/9999-99
	* AAA-9999 
	* (99) 9999-99999
	* [###] A999
	* </pre>
	* */
	public void setMask(String mask){
		if(mask!=null) {
			char[] arr = maskProperty.get() == null ? mask.toCharArray() : maskProperty.get().toCharArray();
			setMaxLenght(mask.length());
			this.maskProperty.set(mask);
			if(getText()!=null){
				textProperty().removeListener(listener);
				setText(TMaskUtil.applyMask(TMaskUtil.removeMask(getText(), arr), maskProperty.get()));
				textProperty().addListener(listener);
			}
		}else {
			if(this.maskProperty.getValue()!=null) {
				String m = this.maskProperty.getValue();
				char[] arr = m.toCharArray();
				setText(TMaskUtil.removeMask(getText(), arr));
			}
			if(maxLenght!=null)
				maxLenght.setValue(null);
			this.maskProperty.setValue(null);
		}
	}
	
	public SimpleStringProperty maskProperty() {
		return maskProperty;
	}
	
	@Override 
	public void replaceText(int start, int end, String letter) {
		
		textProperty().removeListener(listener);
		
		if(maxLenght==null || maxLenght.getValue()==null || maxLenght.getValue()==0 || letter.equals(""))
			super.replaceText(start, end, letter);
		else {
			final int totalASub = end - start;
			final int textLenght = (getText()==null) ? 0 : getText().length();
			if( (textLenght-totalASub) + letter.length() <= maxLenght.get() ){
				if(maskProperty!=null){
					int calculatedEnd = end;
					if(calculatedEnd!=start)
						calculatedEnd = start;
					String ch = TMaskUtil.getMaskChar(start, calculatedEnd, maskProperty.get());
					if(ch!=null){
						if(!TMaskUtil.isCharMaskValid(ch, letter)){
							textProperty().addListener(listener);
							return;
						}
				        while(!(ch.equals("#") || ch.equals("9") || ch.equals("A"))){
				        	super.replaceText(start, calculatedEnd < start ? start : calculatedEnd > textLenght ? textLenght < start ? start : textLenght : calculatedEnd, ch);
							start++; calculatedEnd++;
							ch = TMaskUtil.getMaskChar(start, calculatedEnd, maskProperty.get());
							if(ch == null || !TMaskUtil.isCharMaskValid(ch, letter)){
								textProperty().addListener(listener);
								return;
							}
				        }
					}
				}
				super.replaceText(start, end < start ? start : end > textLenght ? textLenght : end, letter);
			}
		}
		textProperty().addListener(listener);
	}
	
	@Override 
	public void replaceSelection(String selection) {
		
		if(maxLenght==null)
			super.replaceSelection(selection);
		else{
			if(maskProperty.getValue()!=null) {
				final String selectedText  = getSelectedText();
				final int selectedTextLenght = (selectedText!=null) ? selectedText.length() : 0;
				final int textLenght = getText()==null ? 0 : getText().length();
				final int maxLenghtValue = maxLenght.get();
				while( (textLenght - selectedTextLenght) + selection.length() > maxLenghtValue){
					selection = selection.substring(0, selection.length()-1);
				}
				String text =TMaskUtil.applyMask(selection, maskProperty.get());
				super.replaceSelection(text);
			}else
				super.replaceSelection(selection);
		}
		 
	}
	
	/**
	 * Set the max lenght
	 * */
	public void setMaxLenght(int maxLenght) {
		if(this.maxLenght==null)
			this.maxLenght = new SimpleIntegerProperty();
		this.maxLenght.setValue(maxLenght);;
	}
	/**
	 * Get the maxLenghtProperty
	 * */
	public SimpleIntegerProperty maxLenghtProperty(){
		return maxLenght;
	}
	
}
