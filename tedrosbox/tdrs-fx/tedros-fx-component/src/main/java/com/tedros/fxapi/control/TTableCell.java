/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 25/02/2014
 */
package com.tedros.fxapi.control;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.Property;
import javafx.scene.control.TableCell;

import com.tedros.fxapi.properties.TFXComponentsPropertiesUtil;
import com.tedros.fxapi.properties.TFXComponentsPropertiesUtil.PatternKeyValue;
import com.tedros.fxapi.util.TMaskUtil;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TTableCell<S, T> extends TableCell<S, T> {
	
	private String mask;
	private String datePattern = TFXComponentsPropertiesUtil.getPatternValue(PatternKeyValue.date_format);
	
	public TTableCell() {
		
	}
	
	public TTableCell(String mask, String datePattern) {
		checkMask(mask);
		this.mask = mask;
		if(datePattern != null)
			this.datePattern = datePattern;
	}
	
	public String processItem(T item){
		return null;
	}
	
	
	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		
		if(item!=null){
			
			String tmp = processItem(item);
			if(tmp!=null)
				setCellText(tmp);
			else 
			if(item instanceof Property<?>){
				Object obj = ((Property<?>)item).getValue();
				if(obj != null){
					if(obj instanceof String)
						setCellText((String) obj);
					if(obj instanceof Date)
						setCellDate((Date) obj);
				}
			}
		}else
			setText(null);
	}
	
	private void setCellText(String text){
		if(mask==null)
			setText(text);
		else
			setText(TMaskUtil.applyMask(text, mask));
	}
	
	private void setCellDate(Date date){
		setText(new SimpleDateFormat(datePattern).format(date));
	}

	public final String getMask() {
		return mask;
	}

	public final void setMask(String mask) {
		checkMask(mask);
		this.mask = mask;
	}

	public final String getDatePattern() {
		return datePattern;
	}

	public final void setDatePattern(String datePattern) {
		if(datePattern == null)
			throw new IllegalArgumentException("\n\nTERROR :: null values not allowed for method setDatePattern in "+getClass().getSimpleName()+"\n");
		this.datePattern = datePattern;
	}
	
	private void checkMask(String mask) {
		if(mask == null)
			throw new IllegalArgumentException("\n\nTERROR :: null values not allowed for method setMask in "+getClass().getSimpleName()+"\n");
	}
	
}
