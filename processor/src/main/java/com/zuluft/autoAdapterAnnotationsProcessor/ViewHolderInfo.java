package com.zuluft.autoAdapterAnnotationsProcessor;

import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;

/**
 * Created by giodz on 9/20/2017.
 */

public class ViewHolderInfo {
    public final String name;
    public final ViewInfo[] viewInfos;

    public ViewHolderInfo(String name, ViewInfo[] viewInfos) {
        this.name = name;
        this.viewInfos = viewInfos;
    }
}
