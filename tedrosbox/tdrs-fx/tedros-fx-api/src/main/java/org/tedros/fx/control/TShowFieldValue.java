package org.tedros.fx.control;

import org.tedros.fx.domain.TLabelPosition;

/**
 * The field to read and show 
 * */
public class TShowFieldValue{
	
	private String name;
	private String pattern;
	private String label;
	private TLabelPosition labelPosition;
	
	/**
	 * @param name
	 */
	public TShowFieldValue(String name) {
		this.name = name;
	}
	/**
	 * @param name
	 * @param label
	 */
	public TShowFieldValue(String name, String label) {
		this.name = name;
		this.label = label;
	}
	/**
	 * @param name
	 * @param label
	 * @param labelPosition
	 */
	public TShowFieldValue(String name, String label, TLabelPosition labelPosition) {
		this.name = name;
		this.label = label;
		this.labelPosition = labelPosition;
	}
	/**
	 * @param name
	 * @param pattern
	 * @param label
	 * @param labelPosition
	 */
	public TShowFieldValue(String name, String pattern, String label, TLabelPosition labelPosition) {
		this.name = name;
		this.pattern = pattern;
		this.label = label;
		this.labelPosition = labelPosition;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}
	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the labelPosition
	 */
	public TLabelPosition getLabelPosition() {
		return labelPosition;
	}
	/**
	 * @param labelPosition the labelPosition to set
	 */
	public void setLabelPosition(TLabelPosition labelPosition) {
		this.labelPosition = labelPosition;
	}
	
	
	
	
}