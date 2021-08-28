package com.tedros.ejb.base.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = RUNTIME)
@Target({TYPE, METHOD})
@Documented
//@SecurityBindingType
public @interface CheckClientAutorization {}