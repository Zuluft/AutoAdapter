package com.zuluft.autoAdapterAnnotationsProcessor;

import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;

import java.util.Map;

/**
 * Created by giodz on 9/20/2017.
 */

public class ViewHolderInfo {
    public final String name;
    public final ViewInfo[] viewInfos;

    public ViewHolderInfo(final String name,
                          final ViewField[] viewFields) {
        this.name = name;
        viewInfos = new ViewInfo[viewFields.length];
        for (int i = 0; i < viewInfos.length; i++) {
            ViewField viewField = viewFields[i];
            ViewInfo viewInfo = new ViewInfo(viewField.id(), viewField.name(),
                    ClassNameHelper.getViewClassInfo(viewField));
            viewInfos[i] = viewInfo;
        }
    }
}
