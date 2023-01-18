package org.tedros.web.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;

@Stereotype // we define a stereotype
@Retention(RetentionPolicy.RUNTIME) // resolvable at runtime
@Target(ElementType.TYPE) // this annotation is a class level one
@Alternative // it replace @Alternative
public @interface LoggedInUser {}
