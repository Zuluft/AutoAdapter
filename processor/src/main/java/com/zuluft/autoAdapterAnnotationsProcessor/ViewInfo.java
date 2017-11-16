package com.zuluft.autoAdapterAnnotationsProcessor;

import javax.lang.model.element.TypeElement;


@SuppressWarnings("WeakerAccess")
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
