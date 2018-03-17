/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 08/11/2013
 */
package com.tedros.fxapi.annotation.control;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.tedros.fxapi.annotation.parser.ITAnnotationParser;
import com.tedros.fxapi.annotation.parser.TDatePickerParser;
import com.tedros.fxapi.annotation.parser.TRequiredDatePickerParser;
import com.tedros.fxapi.annotation.scene.TNode;
import com.tedros.fxapi.annotation.scene.control.TComboBoxBase;
import com.tedros.fxapi.annotation.scene.control.TControl;
import com.tedros.fxapi.builder.ITFieldBuilder;
import com.tedros.fxapi.builder.ITGenericBuilder;
import com.tedros.fxapi.builder.NullCalendarBuilder;
import com.tedros.fxapi.builder.NullCallbackCalendarViewDateCellBuilder;
import com.tedros.fxapi.builder.NullDateBuilder;
import com.tedros.fxapi.builder.NullDateFormatBuilder;
import com.tedros.fxapi.builder.NullLocaleBuilder;
import com.tedros.fxapi.builder.TDatePickerFieldBuilder;
import com.tedros.fxapi.control.CalendarView;
import com.tedros.fxapi.control.DateCell;
import com.tedros.fxapi.control.TRequiredDatePicker;
import com.tedros.fxapi.domain.TDefaultValues;

import javafx.scene.Node;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.util.Callback;

/**
 * <pre>
 * Build a {@link com.tedros.fxapi.control.TDatePickerField} component.
 * </pre>
 *
 * @author Davis Gordon
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TDatePickerField  {
	
	/**
	 *<pre>
	 * The builder of type {@link ITFieldBuilder} for this component.
	 * 
	 *  Default value: {@link TDatePickerFieldBuilder}
	 *</pre> 
	 * */
	public Class<? extends ITFieldBuilder> builder() default TDatePickerFieldBuilder.class;
	
	/**
	 * <pre>
	 * The parser class for this annotation
	 * 
	 * Default value: {TRequiredDatePickerParser.class, TDatePickerParser.class}
	 * </pre>
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends ITAnnotationParser>[] parser() default {TRequiredDatePickerParser.class, TDatePickerParser.class};
	
	/**
	 * <pre>
	 * The {@link ComboBoxBase} properties.
	 * </pre>
	 * */
	public TComboBoxBase comboBoxBase() default @TComboBoxBase(parse = false);
	
	/**
	 * <pre>
	 * The {@link Node} properties.
	 * </pre>
	 * */
	public TNode node() default @TNode(parse = false);
	
	/**
	 * <pre>
	 * The {@link Control} properties.
	 * 
	 * Default value: @TControl(prefWidth=250) 
	 * </pre>
	 * */
	public TControl control() default @TControl(prefWidth=TDefaultValues.LABEL_WIDTH, parse = true);
	
	/**
	 * <pre>
     * Sets the calendar.
     * 
     * Property description:
     *
     * The calendar builder
     * </pre>
     */
    public Class<? extends ITGenericBuilder<Calendar>> calendar() default NullCalendarBuilder.class;
	
    /**
     * <pre>
     * Sets the date format.
     * 
     * Property description:
     *
     * The date format.
     * </pre>
     */
    public Class<? extends ITGenericBuilder<DateFormat>> dateFormat() default NullDateFormatBuilder.class;
    
    /**
     * <pre>
     * Sets the cell factory.
     * 
     * Property description:
     *
     * The cell factory.
     * </pre>
     */
    public Class<? extends ITGenericBuilder<Callback<CalendarView, DateCell>>> dayCellFactory() default NullCallbackCalendarViewDateCellBuilder.class;
    
    /**
     * <pre>
     * Sets the locale.
     * 
     * Property description:
     *
     * The locale.
     * </pre>
     */
    public Class<? extends ITGenericBuilder<Locale>> locale() default NullLocaleBuilder.class;
    
    /**
     * <pre>
     * Sets the min date.
     *
     * Property description:
     *
     * The min date.
     * </pre>
     */
    public Class<? extends ITGenericBuilder<Date>> minDate() default NullDateBuilder.class;
    
    /**
     * <pre>
     * Sets the max date.
     *
     * Property description:
     *
     * The max date.
     * </pre>
     */
    public Class<? extends ITGenericBuilder<Date>> maxDate() default NullDateBuilder.class;
    
	/**
	 * <pre>
     * Sets the value, whether weeks are shown.
     * 
     * Property description:
     *
     * True, if weeks are shown, otherwise false.
     * 
     * Default value: true;
     * </pre>
     */
	public boolean showWeeks() default true;
	
	/**
	 * <pre>
	 * {@link TRequiredDatePicker} Class
	 * 
	 * Sets the value of the property required.
	 * 
	 * Property description:
	 * 
	 * Determines with this control will be required.
	 * 
	 * Default value: false.
	 * </pre>
	 * */
	public boolean required() default false;
	
	
}
