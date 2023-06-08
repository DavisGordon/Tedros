/**
 * 
 */
package org.tedros.fx.presenter.paginator;

import org.tedros.fx.annotation.query.TTemporal;
import org.tedros.fx.form.TConverter;
import org.tedros.server.query.TCompareOp;

/**
 * @author Davis Gordon
 *
 */
public final class TSearch {

	String field;
	String alias;
	String label;
	String mask;
	String prompt;
	TCompareOp operator;
	TTemporal temporal;
	@SuppressWarnings("rawtypes")
	Class<? extends TConverter> converter;
	
	/**
	 * @param field
	 * @param alias
	 * @param label
	 * @param mask
	 * @param prompt
	 * @param operator
	 * @param temporal
	 * @param converter
	 */
	@SuppressWarnings("rawtypes")
	public TSearch(String field, String alias, String label, String mask, String prompt, TCompareOp operator,
			TTemporal temporal, Class<? extends TConverter> converter) {
		super();
		this.field = field;
		this.alias = alias;
		this.label = label;
		this.mask = mask;
		this.prompt = prompt;
		this.operator = operator;
		this.temporal = temporal;
		this.converter = converter;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @return the mask
	 */
	public String getMask() {
		return mask;
	}
	/**
	 * @return the temporal
	 */
	public TTemporal getTemporal() {
		return temporal;
	}
	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}
	/**
	 * @return the operator
	 */
	public TCompareOp getOperator() {
		return operator;
	}
	/**
	 * @return the converter
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends TConverter> getConverter() {
		return converter;
	}
}
