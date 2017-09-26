package com.zuluft.autoadapterannotations;


/**
 * Created by giodz on 9/20/2017.
 */

public @interface ViewField {
    int id();

    String name();

    Class<?> type();
}
