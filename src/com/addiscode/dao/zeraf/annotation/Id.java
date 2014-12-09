package com.addiscode.dao.zeraf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * MARKER ANNOTATION
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface Id {}
