package com.zuluft.autoAdapterAnnotationsProcessor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by giodz on 9/20/2017.
 */

public class ViewInfo {
    public final int id;
    public final String name;
    public final TypeElement canonicalName;


    public ViewInfo(final int id,
                    final String name,
                    final TypeElement canonicalName) {
        this.id = id;
        this.name = name;
        this.canonicalName = canonicalName;
    }
}
