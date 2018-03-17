/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/11/2013
 */
package com.tedros.fxapi.control;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * TTextArea MAX LENGHT 
 *
 * @author Davis Gordon
 *
 */
public class TTextAreaField extends TRequiredTextArea {
	
	private SimpleIntegerProperty maxLength;
		
	@Override 
	public void replaceText(int start, int end, String letter) {
		 
		if(letter.equals("") || maxLength==null)
			super.replaceText(start, end, letter);
		else {
			final int totalASub = end - start;
			final int textLenght = (getText()!=null) ? getText().length() : 0;
			if( (textLenght-totalASub) + letter.length() <= maxLength.get() )
				super.replaceText(start, end, letter);
		}
	}

	@Override 
	public void replaceSelection(String selection) {
		
		if(maxLength==null)
			super.replaceSelection(selection);
		else{
			final String selectedText  = getSelectedText();
			final int selectedTextLenght = (selectedText!=null) ? selectedText.length() : 0;
			final int textLenght = getText().length();
			final int maxLenghtValue = maxLength.get();
			
			while( (textLenght - selectedTextLenght) + selection.length() > maxLenghtValue)
				selection = selection.substring(0, selection.length()-1);
			
			super.replaceSelection(selection);
		}
		 
	}
	
	/**
	 * Set the max lenght
	 * */
	public void setMaxLength(int maxLength) {
		if(this.maxLength==null)
			this.maxLength = new SimpleIntegerProperty();
		this.maxLength.setValue(maxLength);;
	}
	/**
	 * Get the maxLenghtProperty
	 * */
	public SimpleIntegerProperty maxLengthProperty(){
		return maxLength;
	}
	
}
