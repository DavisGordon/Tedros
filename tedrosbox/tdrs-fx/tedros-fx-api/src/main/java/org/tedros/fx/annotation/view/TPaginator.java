package org.tedros.fx.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tedros.fx.presenter.model.TModelView;
import org.tedros.server.controller.ITEjbController;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.query.TSelect;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface TPaginator {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(value={ElementType.ANNOTATION_TYPE, ElementType.TYPE})
	public @interface TJoin {
		
		/**
		 * The field alias
		 */
		String fieldAlias() default TSelect.ALIAS;
		/**
		 * The field in target entity to join
		 */
		String field();
		/**
		 * The join alias
		 */
		String joinAlias();
	}
	
	/**
	 * Show the paginator
	 * 
	 * @default false
	 * */
	public boolean show() default false;
	
	/**
	 * Show the search field
	 * 
	 * @default false
	 * */
	public boolean showSearch() default false;
	
	/**
	 * The entity field name to search.
	 * */
	public String searchField() default "";

	/**
	 * The search prompt text.
	 * */
	public String promptText() default "";
	
	/**
	 * The field alias.
	 * */
	public String fieldAlias() default TSelect.ALIAS;
	
	/**
	 * Use this in case of the field to be searched
	 * are in a relational entity.
	 * */
	public TJoin[] join() default {};
	
	/**
	 * The ejb jndi name to lookup the service, this must implement ITEjbController
	 * 
	 * @see ITEjbController
	 * */
	public String serviceName();
	
	/**
	 * The entity class
	 * */
	public Class<? extends ITEntity> entityClass();
	
	/**
	 * The model view class
	 * */
	@SuppressWarnings("rawtypes")
	public Class<? extends TModelView> modelViewClass() default TModelView.class;
	/**
	 * Show the order by combobox
	 * 
	 * @default true
	 * */
	public boolean showOrderBy() default true;
	
	/**
	 * The order by options
	 * 
	 * @default @TOption(text="#{tedros.fxapi.label.code}", value="id")
	 * */
	public TOption[] orderBy() default {@TOption(text="#{tedros.fxapi.label.code}", field="id")};
}
