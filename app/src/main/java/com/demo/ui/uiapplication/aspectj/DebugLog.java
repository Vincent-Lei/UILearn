package com.demo.ui.uiapplication.aspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Vincent.Lei on 2018/10/16.
 * Title：
 * Note：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DebugLog {
    String tag() default "defaultLog";
}
