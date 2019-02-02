package com.pro.jenova.common.messaging;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({CONSTRUCTOR, METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
public @interface ApplicationTopicExchange {

}