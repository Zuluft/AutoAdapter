package com.zuluft.autoAdapterAnnotationsProcessor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zuluft.autoadapterannotations.Render;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class Processor extends AbstractProcessor {

    private static final String VIEW_HOLDER_FACTORY_CLASS_NAME = "ViewHolderFactory";
    private static final String VIEW_HOLDER_SUFFIX = "ViewHolder";
    private static final String GENERATED_CLASSES_PACKAGE_NAME = "com.zuluft.generated";
    private static final String GENERATED_VIEW_HOLDER_FACTORY_CLASS_NAME = "ViewHolderFactoryImpl";

    private static final ClassName sViewHolderSuperClassName =
            ClassName.get("com.zuluft.autoadapter.renderables",
                    "AutoViewHolder");
    private static final ClassName sViewHolderFactorySuperClassName =
            ClassName.get("com.zuluft.autoadapter.factories",
                    "AutoViewHolderFactory");
    private static final ClassName sViewClassName =
            ClassName.get("android.view", "View");
    private static final ClassName sAutoAdapterClassName =
            ClassName.get("com.zuluft.autoadapter", "AutoAdapter");
    private static final ClassName sSortedAutoAdapterClassName =
            ClassName.get("com.zuluft.autoadapter", "SortedAutoAdapter");
    private static final ClassName sViewHolderFactoryImplClassName =
            ClassName.get(GENERATED_CLASSES_PACKAGE_NAME, GENERATED_VIEW_HOLDER_FACTORY_CLASS_NAME);


    private Messager mMessager;
    private Elements mElements;
    private Filer mFiler;
    private List<ViewHolderInfo> mViewHolderInfos;
    private Map<TypeName, Integer> mRendererLayoutIdMapping;
    private Map<Integer, TypeName> mLayoutIdViewHolderMapping;


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<>(Arrays.asList("com.zuluft.autoadapterannotations.ViewHolderFactory",
                "com.zuluft.autoadapterannotations.Render"));
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElements = processingEnvironment.getElementUtils();
        mFiler = processingEnvironment.getFiler();
        mViewHolderInfos = new ArrayList<>();
        mRendererLayoutIdMapping = new HashMap<>();
        mLayoutIdViewHolderMapping = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!checkAnnotatedElements(roundEnvironment)) {
            return true;
        }
        if (!generateViewHolderClasses()) {
            return true;
        }
        if (!generateViewHolderFactoryClass()) {
            return true;
        }
        if (!generateAutoAdapterFactoryClass()) {
            return true;
        }
        return false;
    }

    private boolean generateAutoAdapterFactoryClass() {
        try {
            JavaFile.builder(GENERATED_CLASSES_PACKAGE_NAME,
                    createAutoAdapterFactoryClass())
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private TypeSpec createAutoAdapterFactoryClass() {
        return TypeSpec.classBuilder("AutoAdapterFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(createGetAutoAdapterStaticMethod())
                .addMethod(createGetSortedAutoAdapterStaticMethod())
                .addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build())
                .build();
    }

    private MethodSpec createGetSortedAutoAdapterStaticMethod() {
        return MethodSpec.methodBuilder("createSortedAutoAdapter")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .returns(sSortedAutoAdapterClassName)
                .addStatement("return new $L(new $T())", "SortedAutoAdapter",
                        sViewHolderFactoryImplClassName)
                .build();
    }

    private MethodSpec createGetAutoAdapterStaticMethod() {
        return MethodSpec.methodBuilder("createAutoAdapter")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .returns(sAutoAdapterClassName)
                .addStatement("return new $L(new $T())", "AutoAdapter",
                        sViewHolderFactoryImplClassName)
                .build();
    }

    private boolean generateViewHolderFactoryClass() {
        try {
            JavaFile.builder(GENERATED_CLASSES_PACKAGE_NAME,
                    createViewHolderFactoryClass())
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private TypeSpec createViewHolderFactoryClass() {
        return TypeSpec.classBuilder(GENERATED_VIEW_HOLDER_FACTORY_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(sViewHolderFactorySuperClassName)
                .addMethod(createGetLayoutIdMethod())
                .addMethod(createGetViewHolderMethod())
                .addMethod(createViewHolderFactoryConstructor())
                .build();
    }

    private MethodSpec createViewHolderFactoryConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
    }

    private MethodSpec createGetViewHolderMethod() {
        MethodSpec.Builder builder =
                MethodSpec.methodBuilder("createViewHolder")
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(ClassName
                                        .get("android.view", "ViewGroup"),
                                "parent", Modifier.FINAL).build())
                        .addParameter(TypeName.INT, "layoutId", Modifier.FINAL)
                        .returns(sViewHolderSuperClassName)
                        .addStatement("$T view=createView($L,$L)",
                                sViewClassName, "parent", "layoutId");
        for (Map.Entry<Integer, TypeName> entry :
                mLayoutIdViewHolderMapping.entrySet()) {
            builder.beginControlFlow("if($L == $L)", "layoutId", entry.getKey())
                    .addStatement("return new $T($L)", entry.getValue(), "view")
                    .endControlFlow();
        }
        builder.addStatement("return null");
        return builder.build();
    }

    private MethodSpec createGetLayoutIdMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("getLayoutId")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(
                        ClassName.get("com.zuluft.autoadapter.renderables", "IRenderer"), "renderer", Modifier.FINAL)
                        .build())
                .returns(TypeName.INT);
        for (Map.Entry<TypeName, Integer> entry : mRendererLayoutIdMapping.entrySet()) {
            builder.beginControlFlow("if($L.getClass().equals($T.class))",
                    "renderer", entry.getKey())
                    .addStatement("return $L", entry.getValue())
                    .endControlFlow();
        }
        builder.addStatement("return -1");
        return builder.build();
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

    private boolean generateViewHolderClasses() {
        for (ViewHolderInfo viewHolderInfo : mViewHolderInfos) {
            try {
                JavaFile.builder(GENERATED_CLASSES_PACKAGE_NAME,
                        createViewHolderClass(viewHolderInfo))
                        .build().writeTo(mFiler);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }


    private Iterable<FieldSpec> createViewHolderFields(ViewInfo[] viewInfos) {
        List<FieldSpec> fieldSpecs = new ArrayList<>();
        for (ViewInfo viewInfo : viewInfos) {
            fieldSpecs.add(FieldSpec.builder(ClassName.get(viewInfo.canonicalName),
                    viewInfo.name)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
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

    private boolean checkAnnotatedElements(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Render.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                mMessager.printMessage(Diagnostic.Kind.ERROR,
                        "Only classes can be annotated with @Render annotation");
                return false;
            }

            Render renderAnnotation = element.getAnnotation(Render.class);
            String viewHolderClassName = element.getSimpleName().toString() + VIEW_HOLDER_SUFFIX;
            mViewHolderInfos.add(new
                    ViewHolderInfo(viewHolderClassName,
                    renderAnnotation.views()));
            mRendererLayoutIdMapping.put(ClassName.get(element.asType()),
                    renderAnnotation.layout());
            mLayoutIdViewHolderMapping.put(renderAnnotation.layout(),
                    ClassName.get(GENERATED_CLASSES_PACKAGE_NAME, viewHolderClassName));
        }
        return true;
    }
}
