/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/11/2013
 */
package com.tedros.fxapi.control;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * TextField MAX LENGHT Example 
 *
 * @author Davis Gordon
 *
 */
public class TPasswordField extends TRequiredPasswordField {
	
	private SimpleIntegerProperty maxLenght;
		
	@Override 
	public void replaceText(int start, int end, String letter) {
		 
		if(letter.equals("") || maxLenght==null)
			super.replaceText(start, end, letter);
		else {
			final String text = getText();
			final int totalASub = end - start;
			final int textLenght = text!=null ? text.length() : 0;
			if( (textLenght-totalASub) + letter.length() <= maxLenght.get() )
				super.replaceText(start, end, letter);
		}
	}

	@Override 
	public void replaceSelection(String selection) {
		
		if(maxLenght==null)
			super.replaceSelection(selection);
		else{
			final String selectedText  = getSelectedText();
			final int selectedTextLenght = (selectedText!=null) ? selectedText.length() : 0;
			final int textLenght = getText().length();
			final int maxLenghtValue = maxLenght.get();
			
			while( (textLenght - selectedTextLenght) + selection.length() > maxLenghtValue)
				selection = selection.substring(0, selection.length()-1);
			
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
