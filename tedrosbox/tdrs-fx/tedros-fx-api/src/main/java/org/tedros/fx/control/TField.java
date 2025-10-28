package org.tedros.fx.control;

import org.tedros.fx.converter.TConverter;
import org.tedros.fx.domain.TDateStyle;
import org.tedros.fx.domain.TLabelPosition;
import org.tedros.fx.domain.TTimeStyle;

/**
 * The field to read and show 
 * */
public class TField{
	
	private String name;
	private String mask;
	private String format;
	private TDateStyle dateStyle;
	private TTimeStyle timeStyle;
	private String label;
	private TLabelPosition labelPosition;
	@SuppressWarnings("rawtypes")
	private Class<? extends TConverter> converter;
	
	/**
	 * @param name
	 * @param mask
	 * @param format
	 * @param dateStyle
	 * @param timeStyle
	 * @param label
	 * @param labelPosition
	 * @param converter
	 */
	@SuppressWarnings("rawtypes")
	public TField(String name, String mask, String format, TDateStyle dateStyle, TTimeStyle timeStyle, String label,
			TLabelPosition labelPosition, Class<? extends TConverter> converter) {
		super();
		this.name = name;
		this.mask = mask;
		this.format = format;
		this.dateStyle = dateStyle;
		this.timeStyle = timeStyle;
		this.label = label;
		this.labelPosition = labelPosition;
		this.converter = converter;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the mask
	 */
	public String getMask() {
		return mask;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the labelPosition
	 */
	public TLabelPosition getLabelPosition() {
		return labelPosition;
	}

	/**
	 * @return the dateStyle
	 */
	public TDateStyle getDateStyle() {
		return dateStyle;
	}

	/**
	 * @return the timeStyle
	 */
	public TTimeStyle getTimeStyle() {
		return timeStyle;
	}

	/**
	 * @return the converter
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends TConverter> getConverter() {
		return converter;
	}
}