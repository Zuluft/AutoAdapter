package com.zuluft.autoAdapterAnnotationsProcessor;

import javax.lang.model.type.TypeMirror;

/**
 * Created by giodz on 9/20/2017.
 */

public class ViewInfo {
    public final int id;
    public final String name;
    public final TypeMirror type;


    public ViewInfo(int id, String name, TypeMirror type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
