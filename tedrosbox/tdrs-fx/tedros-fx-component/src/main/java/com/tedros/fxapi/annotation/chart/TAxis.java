package com.tedros.fxapi.annotation.chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javafx.geometry.Side;

import com.tedros.fxapi.annotation.text.TFont;
import com.tedros.fxapi.domain.TAxisType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface TAxis {
	
	/**
	 * The axis type
	 * */
	public TAxisType axisType();
	
	/**
	 * The axis label
	 * */
	public String  label();
	
	/**
	 * The number of minor tick divisions to be displayed between each major tick mark. 
	 * The number of actual minor tick marks will be one less than this.
	 * Only for TAxisType.NUMBER
	 * */
	public int minorTickCount() default 1;
	
	/**
	 * The length of minor tick mark lines. Set to 0 to not display minor tick marks.
	 * Only for TAxisType.NUMBER
	 * */
	public int minorTickLength() default 1;
	
	/**
	 * true if minor tick marks should be displayed
	 * Only for TAxisType.NUMBER
	 * */
	public boolean minorTickVisible() default true;
	
	/**
	 * The value between each major tick mark in data units.
	 * Only for TAxisType.NUMBER
	 * */
	public int tickUnit() default 10;
	
	/**
	 * The fill for all tick labels
	 * */
	public String tickLabelFill() default "#ffffff";
	
	/**
	 * The gap between tick labels and the tick mark lines
	 * */
	public double tickLabelGap() default 22D;
	
	/**
	 * Rotation in degrees of tick mark labels from their normal horizontal.
	 * */
	public double  tickLabelRotation() default 30D;
	
	/**
	 * true if tick marks should be displayed
	 * */
	public boolean tickMarkVisible() default true;
	
	/**
	 * The font for all tick labels
	 * */
	public TFont tickLabelFont() default @TFont();
	
	/**
	 * true if tick mark labels should be displayed
	 * */
	public boolean tickLabelsVisible() default true;
	
	/**
	 * Sets the value of the property forceZeroInRange.
	 * Property description:
	 * When true zero is always included in the visible range. This only has effect if auto-ranging is on.
	 * Only for TAxisType.NUMBER
	 * */
	public boolean forceZeroInRange() default false;
	/**
	 * This is true when the axis determines its range from the data automatically
	 * */
	public boolean autoRanging() default true;
	
	/**
	 * The length of tick mark lines
	 * */
	public double tickLength() default -1; 
	
	/**
	 * The side of the plot which this axis is being drawn on
	 * */
	public Side side() default Side.BOTTOM;
	
	/**
	 * When true any changes to the axis and its range will be animated.
	 * */
	public boolean animated() default true;
	
	/**
	 * The margin between the axis start and the first tick-mark
	 * Only for TAxisType.STRING or TAxisType.DATE
	 * */
	public double startMargin() default -1;
	
	/**
	 * The margin between the last tick mark and the axis end
	 * Only for TAxisType.STRING or TAxisType.DATE
	 * */
	public double endMargin() default -1;
	
	/**
	 * If this is true then half the space between ticks is left at the start and end
	 * Only for TAxisType.STRING or TAxisType.DATE
	 * */
	public boolean gapStartAndEnd() default false;
	
	
}
