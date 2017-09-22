package com.zuluft.autoAdapterAnnotationsProcessor;

import android.view.View;

import com.squareup.javapoet.ClassName;
import com.zuluft.autoadapterannotations.ViewField;

import java.util.AbstractMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Created by giodz on 9/21/2017.
 */

public final class ClassNameHelper {

    public static TypeElement getViewClassInfo(final ViewField viewField) {
        TypeElement typeElement = null;
        try {
            Class<? extends View> type = viewField.type();
            String simpleName = type.getSimpleName();
            String canonicalName = type.getCanonicalName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            typeElement = (TypeElement) classTypeMirror.asElement();

        }
        return typeElement;
    }
}
