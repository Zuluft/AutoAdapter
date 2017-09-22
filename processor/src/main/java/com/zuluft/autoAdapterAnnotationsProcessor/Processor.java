package com.zuluft.autoAdapterAnnotationsProcessor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;
import com.zuluft.autoadapterannotations.ViewHolderFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({
        "com.zuluft.autoadapterannotations.ViewHolderFactory",
        "com.zuluft.autoadapterannotations.Render"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class Processor extends AbstractProcessor {

    private static final String VIEW_HOLDER_SUFFIX = "ViewHolder";
    private static final String GENERATED_CLASSES_PACKAGE_NAME = "com.zuluft.generated";

    private static final ClassName sViewHolderSuperClassName =
            ClassName.get("com.zuluft.autoadapter.renderables",
                    "AutoViewHolder");
    private static final ClassName sViewClassName =
            ClassName.get("android.view", "View");

    private Messager mMessager;
    private Elements mElements;
    private Filer mFiler;
    private List<ViewHolderInfo> mViewHolderInfos;
    private Map<String, String> mAnnotatedViewHolderFactoryInterfaces;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mViewHolderInfos = new ArrayList<>();
        mAnnotatedViewHolderFactoryInterfaces = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        checkAnnotatedElements(roundEnvironment);
        generateViewHolderClasses();
        return false;
    }

    private void generateViewHolderClasses() {
        for (ViewHolderInfo viewHolderInfo : mViewHolderInfos) {
            try {
                JavaFile.builder(GENERATED_CLASSES_PACKAGE_NAME,
                        createViewHolderClass(viewHolderInfo)).
                        build().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private TypeSpec createViewHolderClass(ViewHolderInfo viewHolderInfo) {
        return TypeSpec
                .classBuilder(viewHolderInfo.name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addFields(createViewHolderFields(viewHolderInfo.viewInfos))
                .superclass(sViewHolderSuperClassName)
                .addMethod(createConstructorForViewHolder(viewHolderInfo.viewInfos))
                .build();
    }

    private Iterable<FieldSpec> createViewHolderFields(ViewInfo[] viewInfos) {
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        for (ViewInfo viewInfo : viewInfos) {
            fieldSpecs.add(FieldSpec.builder(ClassName.get(viewInfo.canonicalName),
                    viewInfo.name)
                    .addModifiers(Modifier.PUBLIC)
                    .build());
        }
        return fieldSpecs;
    }


    private MethodSpec createConstructorForViewHolder(ViewInfo[] viewInfos) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(sViewClassName,
                        "itemView",
                        Modifier.FINAL)
                        .build())
                .addStatement("super(itemView)");

        for (ViewInfo viewInfo : viewInfos) {
            builder.addStatement("$L=($T)findViewById($L)", viewInfo.name,
                    ClassName.get(viewInfo.canonicalName), viewInfo.id);
        }
        return builder.build();
    }

    private void checkAnnotatedElements(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Render.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                mMessager.printMessage(Diagnostic.Kind.ERROR,
                        "Only classes can be annotated with @Render annotation");
            }
            Render renderAnnotation = element.getAnnotation(Render.class);
            mViewHolderInfos.add(new
                    ViewHolderInfo(element.getSimpleName().toString() + VIEW_HOLDER_SUFFIX,
                    renderAnnotation.views()));

        }
        for (Element element : roundEnvironment.getElementsAnnotatedWith(ViewHolderFactory.class)) {
            if (element.getKind() != ElementKind.INTERFACE) {
                mMessager.printMessage(Diagnostic.Kind.ERROR,
                        "Only interfaces can be annotated with @ViewHolderFactory annotation");
            }
            mAnnotatedViewHolderFactoryInterfaces.put(element.getSimpleName().toString(),
                    mElements.getPackageOf(element).getQualifiedName().toString());
        }

    }
}
