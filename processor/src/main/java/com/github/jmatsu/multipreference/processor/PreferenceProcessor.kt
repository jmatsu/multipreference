package com.github.jmatsu.multipreference.processor

import com.github.jmatsu.multipreference.processor.exception.PreferenceValidationException
import com.github.jmatsu.multipreference.processor.util.getNonNullAnnotation
import com.github.jmatsu.multipreference.processor.util.getNullableAnnotation
import com.squareup.javapoet.JavaFile
import java.util.concurrent.atomic.AtomicReferenceArray
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic

private val processEnvReference: AtomicReferenceArray<ProcessingEnvironment> = AtomicReferenceArray(1)
val elementUtils: Elements by lazy { processEnvReference[0].elementUtils }
val typeUtils: Types by lazy { processEnvReference[0].typeUtils }
val filer: Filer by lazy { processEnvReference[0].filer }
val messager: Messager by lazy { processEnvReference[0].messager }
val nonNullAnnotation: Class<out Annotation>? by lazy { getNonNullAnnotation() }
val nullableAnnotation: Class<out Annotation>? by lazy { getNullableAnnotation() }

class PreferenceProcessor : AbstractProcessor() {

    override fun init(processEnv: ProcessingEnvironment) {
        super.init(processEnv)
        processEnvReference[0] = processEnv
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(PreferenceAnnotation::class.java)
                .map { PreferenceModel(it.getAnnotation(PreferenceAnnotation::class.java), it as TypeElement) }
                .forEach { prefModel ->
                    try {
                        prefModel.validate()
                        val typeSpec = prefModel.toTypeSpec()
                        JavaFile.builder(prefModel.packageName, typeSpec).build().writeTo(filer)
                    } catch (e: PreferenceValidationException) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "[PreferenceValidationException] while processing ${prefModel.elementName}, ${e.message}")

                        when (e) {
                            is PreferenceValidationException.TypeSpecBuildException -> {
                                e.throwables.forEach {
                                    messager.printMessage(Diagnostic.Kind.ERROR, "[${it.javaClass.simpleName}] while processing ${prefModel.elementName}, ${it.message}")
                                }
                            }
                            is PreferenceValidationException.InvalidModifier -> {
                                // no-op
                            }
                        }
                    } catch (e: Exception) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "[Unknown Error] While processing ${prefModel.elementName}, ${e.message}")
                        e.printStackTrace()
                    }
                }

        return true
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        return hashSetOf(PreferenceAnnotation::class.java.canonicalName)
    }
}