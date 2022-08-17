/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 10/01/2014
 */
package org.tedros.fx.builder;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.apache.commons.lang3.StringUtils;
import org.tedros.fx.annotation.reader.TReader;
import org.tedros.fx.presenter.model.TModelView;
import org.tedros.fx.reader.TBigDecimalReader;
import org.tedros.fx.reader.TBigIntegerReader;
import org.tedros.fx.reader.TBooleanReader;
import org.tedros.fx.reader.TDateTimeReader;
import org.tedros.fx.reader.TDoubleReader;
import org.tedros.fx.reader.TFloatReader;
import org.tedros.fx.reader.TIntegerReader;
import org.tedros.fx.reader.TLongReader;
import org.tedros.fx.reader.TStringReader;
import org.tedros.fx.reader.TTextReader;
import org.tedros.server.entity.ITEntity;


/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TReaderBuilder 
extends TBuilder
implements ITReaderBuilder<TTextReader, Property> {

	public TTextReader build(final Annotation annotation, final Property attrProperty) throws Exception {
		TReader tAnnotation = (TReader) annotation;
		TTextReader<?> reader = generateReader(tAnnotation, attrProperty); 
		callParser(tAnnotation, reader);
		if(reader!=null && StringUtils.isBlank(reader.getId()))
			reader.setId("t-reader-text");
		return reader;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private TTextReader<?> generateReader(TReader tAnnotation, Property<?> property) {
		
		TTextReader<?> reader = null;
		if(property instanceof SimpleStringProperty){
			reader = new TStringReader();
			((TStringReader)reader).valueProperty().bind((SimpleStringProperty)property);
			if(StringUtils.isNotBlank(tAnnotation.mask()))
				((TStringReader)reader).setMask(tAnnotation.mask());
			
		}
		
		if(property instanceof SimpleLongProperty){
			reader = new TLongReader();
			((TLongReader)reader).valueProperty().bind((SimpleLongProperty)property);
		}
		
		if(property instanceof SimpleIntegerProperty){
			reader = new TIntegerReader();
			((TIntegerReader)reader).valueProperty().bind((SimpleIntegerProperty)property);
		}
		
		if(property instanceof SimpleDoubleProperty){
			reader = new TDoubleReader();
			((TDoubleReader)reader).valueProperty().bind((SimpleDoubleProperty)property);
		}
		
		if(property instanceof SimpleFloatProperty){
			reader = new TFloatReader();
			((TFloatReader)reader).valueProperty().bind((SimpleFloatProperty)property);
		}
		
		if(property instanceof BooleanProperty){
			reader = new TBooleanReader(tAnnotation.booleanValues().trueValue(), tAnnotation.booleanValues().falseValue());
			((TBooleanReader)reader).valueProperty().bind((BooleanProperty)property);
		}
		
		if(property instanceof SimpleObjectProperty){
			Object objectValue = property.getValue();
			
			if(objectValue==null)
				return new TStringReader("");
			
			if(objectValue instanceof TModelView){
				return new TStringReader(((TModelView)objectValue).toStringProperty().getValue());
			}
			
			if(objectValue instanceof Number){
				if(objectValue instanceof BigDecimal){
					reader = new TBigDecimalReader();
					((TBigDecimalReader)reader).valueProperty().bind((SimpleObjectProperty<BigDecimal>)property);
				}
				
				if(objectValue instanceof BigInteger){
					reader = new TBigIntegerReader();
					((TBigIntegerReader)reader).valueProperty().bind((SimpleObjectProperty<BigInteger>)property);
				}
				
				if(objectValue instanceof Double){
					reader = new TDoubleReader();
					((TDoubleReader)reader).valueProperty().bind((SimpleObjectProperty<Double>)property);
				}
				
				if(objectValue instanceof Integer){
					reader = new TIntegerReader();
					((TIntegerReader)reader).valueProperty().bind((SimpleObjectProperty<Integer>)property);
				}
				
				if(objectValue instanceof Long){
					reader = new TLongReader();
					((TLongReader)reader).valueProperty().bind((SimpleObjectProperty<Long>)property);
				}
				
				if(objectValue instanceof Float){
					reader = new TFloatReader();
					((TFloatReader)reader).valueProperty().bind((SimpleObjectProperty<Float>)property);
				}
				
			}else if (objectValue instanceof Date){
				reader = new TDateTimeReader();
				((TDateTimeReader)reader).setDatePattern(tAnnotation.datePattern());
				((TDateTimeReader)reader).valueProperty().bind((SimpleObjectProperty<Date>) property);
				
			}else if(objectValue instanceof ITEntity){
				String value = ((ITEntity) objectValue).toString();
				final TStringReader entityReader = new TStringReader(value);
				property.addListener(new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<? extends Object> arg0,
							Object arg1, Object arg2) {
						entityReader.setValue(arg2.toString());
					}
				});
				reader = entityReader;
			}
		}
		return reader;
	}
	
}
