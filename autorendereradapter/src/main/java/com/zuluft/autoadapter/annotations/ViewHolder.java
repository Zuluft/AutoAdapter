package com.zuluft.autoadapter.annotations;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by giodz on 11/29/2016.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ViewHolder {
    @LayoutRes int value();
}
