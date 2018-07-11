package com.pro.jenova.omnidrive.msg;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({TYPE, METHOD, PARAMETER, FIELD})
public @interface UserEventsQueue {

}

