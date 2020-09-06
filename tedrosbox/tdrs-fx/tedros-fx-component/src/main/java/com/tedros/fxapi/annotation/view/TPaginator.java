package com.tedros.fxapi.annotation.view;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Target;

import com.tedros.ejb.base.entity.ITEntity;

@Target(ANNOTATION_TYPE)
public @interface TPaginator {
	
	public boolean show() default false;
	public boolean showSearchField() default false;
	public String searchFieldName() default "";
	
	public String serviceName();
	public Class<? extends ITEntity> entityClass();
	
	public boolean showOrderBy() default true;
	public TOption[] orderBy() default {@TOption(text="Codigo", value="id")};
}
