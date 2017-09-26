package com.zuluft.autoAdapterAnnotationsProcessor;


import com.zuluft.autoadapterannotations.ViewField;

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
            Class<?> type = viewField.type();
            String simpleName = type.getSimpleName();
            String canonicalName = type.getCanonicalName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            typeElement = (TypeElement) classTypeMirror.asElement();

        }
        return typeElement;
    }
}
