package com.zuluft.autoadapterannotations;

import android.view.View;

/**
 * Created by giodz on 9/20/2017.
 */

public @interface ViewField {
    int id();

    String name();

    Class<? extends View> type();
}
