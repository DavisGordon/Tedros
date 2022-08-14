package org.tedros.fx.control;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

public abstract class TNumberField<N extends Number> extends TRequiredNumberField {
	
	protected SimpleIntegerProperty precision;
	protected SimpleIntegerProperty maxLength;
	private SimpleObjectProperty<N> valueProperty;
	
	public TNumberField() {
		init();
	}
	
	public TNumberField(N value) {
		init();
        valueProperty.set(value);
	}
	
	abstract Class<?> getNumberClassType();

	@SuppressWarnings("rawtypes")
	private void init() {
		
        valueProperty = new SimpleObjectProperty<>();
        precision = new SimpleIntegerProperty(2);
        
        final TNumberField field = this;
        
        textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,
					final String old_value, final String new_value) {
				
				final Class<?> clazz = getNumberClassType();
				
				if(StringUtils.isNotBlank(new_value) && NumberUtils.isCreatable(new_value)){
					
					try{
						parseNumber(new_value, clazz);
						
					}catch(NumberFormatException e){
						
						final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								field.setEffect(null);
								field.setDisable(false);
								textProperty().set(old_value);
							}
						}));
						
						field.setEffect(buildNumberParseExceptionEffect());
						Number maxValue = getMaxVAlue(clazz);
						if(maxValue!=null)
							textProperty().set(" <= "+maxValue);
						else
							textProperty().set(" <> "+new_value);
						timeline.play();
					}
				}else if(StringUtils.isBlank(new_value)){
					parseNumber(null, clazz);
				}
			}

			@SuppressWarnings("unchecked")
			private void parseNumber(String new_value, Class<?> obj) {
				if(obj == BigInteger.class)
					valueProperty.setValue(new_value==null ? null : (N) new BigInteger(new_value));
				if(obj == BigDecimal.class)
					valueProperty.setValue(new_value==null ? null : (N) new BigDecimal(new_value));
				if(obj == Double.class)
					valueProperty.setValue(new_value==null ? null : (N) new Double(new_value));
				if(obj == Long.class)
					valueProperty.setValue(new_value==null ? null : (N) new Long(new_value));
				if(obj == Integer.class)
					valueProperty.setValue(new_value==null ? null : (N) new Integer(new_value));
			}
			
			private Number getMaxVAlue(Class<?> obj) {
				if(obj == Double.class)
					return Double.MAX_VALUE;
				if(obj == Long.class)
					return Long.MAX_VALUE;
				if(obj == Integer.class)
					return Integer.MAX_VALUE;
				return null;
			}
		});
        
        valueProperty.addListener(new ChangeListener<N>() {
			@Override
			public void changed(ObservableValue<? extends N> arg0,
					N arg1, N new_value) {
				if(new_value!=null)
					setText(new_value.toString());
			}
		});
	}
	
	@Override 
	public void replaceText(int start, int end, String text) {
		// aceita backspace 
		if(text.equals("")) {
			try {
				super.replaceText(start, end, text);
			}catch(IllegalArgumentException e) {
				
			}
		}
        // nao aceita caracteres, somente numero e '.' (ponto) 
        if( (isNumberWithDecimal() && !text.equals(".") && !text.matches("[0-9]")) 
        		|| (!isNumberWithDecimal() && (text.equals(".") || !text.matches("[0-9]"))))
			return;
        
        // valida o tamanho maximo do campo
        final int totalASub = end - start;
		final int textLenght = getText().length();
		if( maxLength==null || ( maxLength!=null && maxLength.get()>0 && (textLenght-totalASub) + text.length() <= maxLength.get())){
			// formata o campo
			String t = getText() == null ? "" : getText();
	        t = t.substring(0, start) + text + t.substring(end);
			if (accept(t))
				super.replaceText(start, end, text);
		}
        
    }
	
    @Override 
    public void replaceSelection(String text) {
    	
    	// aceita backspace 
		if(text.equals(""))
			super.replaceSelection(text);
    	
    	if(!NumberUtils.isCreatable(text))
    		return;
    	
    	if(!isNumberWithDecimal())
    		text = text.contains(".") ? text.substring(0, text.lastIndexOf(".")) : text;
    	
    	if(maxLength!=null && maxLength.get()>0 && text.length() > maxLength.get())
    		text = text.substring(0, maxLength.get());
        
    	String t = getText() == null ? "" : getText();
        int start = Math.min(getAnchor(), getCaretPosition());
        int end = Math.max(getAnchor(), getCaretPosition());
        t = t.substring(0, start) + text + t.substring(end);
        if (accept(t)) {
            super.replaceSelection(text);
        }
    }
	
	
	private boolean accept(String text) {
		
		if(!isNumberWithDecimal())
			return true;
		
		text = text.trim();
        if (text.length() == 0) return true;

        final char decimalSeparator = '.';  
        
        int decimalSeparatorIndex = -1;
        for (int i=0; i<text.length(); i++) {
            char ch = text.charAt(i);
            if (decimalSeparatorIndex == -1 && ch == decimalSeparator) {
                decimalSeparatorIndex = i;
            } else if (!Character.isDigit(ch)) {
                return false;
            }
        }

        // aplica a precisao
        if (decimalSeparatorIndex != -1) {
            int trailingLength = text.substring(decimalSeparatorIndex+1).length();
            if (trailingLength > precision.get()) return false;
        }
        
        return true;
    }
	
	private boolean isNumberWithDecimal(){
		final Class<?> obj = getNumberClassType();
		return (obj == BigDecimal.class || obj == Double.class);
	}
	
	private ColorAdjust buildNumberParseExceptionEffect() {
		ColorAdjust ca = new ColorAdjust();
		ca.setHue(0.33587786259541996);
		ca.setSaturation(0.6870229007633586);
		ca.setBrightness(0.03053435114503822);
		ca.setContrast(-0.030534351145038108);
		return ca;
	}
	
	/**
	 * {@link SimpleObjectProperty} of {@link BigDecimal}
	 * */
	public SimpleObjectProperty<N> valueProperty(){
		return valueProperty;
	}
	
	/**
	 * Get the {@link BigDecimal} value
	 * */
	public N getValue(){
		return valueProperty!=null ? valueProperty.get() : null;
	}
	
	/**
	 * Set the {@link BigDecimal} value
	 * */
	public void setValue(final N value){
		if(valueProperty==null) 
			valueProperty = new SimpleObjectProperty<>();
		valueProperty.set(value);
	}
	
	/**
	 * Set the max lenght
	 * */
	public void setMaxLength(int maxLenght) {
		if(this.maxLength==null)
			this.maxLength = new SimpleIntegerProperty();
		this.maxLength.setValue(maxLenght);;
	}
	
	/**
	 * Get the max lenght
	 * */
	public int getMaxLength() {
		if(this.maxLength==null)
			this.maxLength = new SimpleIntegerProperty();
		return this.maxLength.get();
	}
	
	/**
	 * Get the maxLenghtProperty
	 * */
	public SimpleIntegerProperty maxLengthProperty(){
		return maxLength;
	}
	
	/**
	 * Set the precision
	 * */
	public void setPrecision(int precision) {
		if(this.precision==null)
			this.precision = new SimpleIntegerProperty();
		this.precision.setValue(precision);;
	}
	
	/**
	 * Get the precision
	 * */
	public int getPrecision() {
		if(this.precision==null)
			this.precision = new SimpleIntegerProperty();
		return this.precision.get();
	}
	
	/**
	 * Get the maxLenghtProperty
	 * */
	public SimpleIntegerProperty precisionProperty(){
		return precision;
	}
	
}
