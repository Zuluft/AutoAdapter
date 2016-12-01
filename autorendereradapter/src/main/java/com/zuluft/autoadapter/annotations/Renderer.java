package com.zuluft.autoadapter.annotations;

import com.zuluft.autoadapter.renderables.AutoViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by giodz on 11/29/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Renderer {
    Class<? extends AutoViewHolder> value() default AutoViewHolder.class;
}
