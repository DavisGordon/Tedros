/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 27/03/2014
 */
package org.tedros.fx.reader;

import java.math.BigDecimal;

import org.tedros.fx.util.TMaskUtil;
import org.tedros.util.TLoggerUtil;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public class TBigDecimalReader extends TTextReader<BigDecimal> {
	
	private SimpleObjectProperty<BigDecimal> valueProperty;

	public TBigDecimalReader() {
		
	}
	
	public TBigDecimalReader(BigDecimal value) {
		setValue(value);
	}
	
	public void setValue(BigDecimal value){
		valueProperty().setValue(value);
	}
	
	public BigDecimal getValue(){
		return valueProperty!=null ? valueProperty.getValue() : null;
	}
	
	public SimpleObjectProperty<BigDecimal> valueProperty() {
		try {
			buildValueProperty();
			return valueProperty;
		} catch (InstantiationException | IllegalAccessException e) {
			TLoggerUtil.error(getClass(), e.getMessage(), e);
		}
		return null;
	}

	private void buildValueProperty() throws InstantiationException, IllegalAccessException {
		if(valueProperty==null)
			valueProperty = new SimpleObjectProperty<>();
		valueProperty.addListener(new ChangeListener<BigDecimal>() {
			@Override
			public void changed(ObservableValue<? extends BigDecimal> arg0, BigDecimal arg1, BigDecimal arg2) {
				if(arg2!=null)
					setText(getMask()!=null ? TMaskUtil.applyMask(String.valueOf(arg2), getMask()) : String.valueOf(arg2) );
			}
		});
	}
	
}
