/**
 * 
 */
package org.tedros.fx.annotation.query;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.tedros.fx.builder.TGenericBuilder;
import org.tedros.fx.form.TConverter;
import org.tedros.server.query.TCompareOp;
import org.tedros.server.query.TLogicOp;
import org.tedros.server.query.TSelect;

@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
/**
 * @author Davis Gordon
 *
 */
public @interface TCondition {

	TTemporal temporal() default TTemporal.NONE;
	
	TLogicOp logicOp() default TLogicOp.AND;
	
	String alias() default TSelect.ALIAS;
	
	String field();
	
	TCompareOp operator() default TCompareOp.EQUAL;
	
	String value() default "";
	
	@SuppressWarnings("rawtypes")
	Class<? extends TConverter> converter() default TConverter.class;
	
	boolean prompted() default true;
	
	String label() default "";
	
	String promptMask() default "";
	
	String promptText() default "";
	
	@SuppressWarnings("rawtypes")
	Class<? extends TGenericBuilder> valueBuilder() default TGenericBuilder.class;
}
