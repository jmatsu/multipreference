package com.github.jmatsu.multipreference.processor.extension

import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.PackageElement
import javax.lang.model.element.VariableElement

val Element.name: String
    get() = when (this) {
        is PackageElement -> "$qualifiedName"
        else -> "$simpleName"
    }

inline fun <reified T> Element.annotation(): T? where T : Annotation = getAnnotation(T::class.java)

fun Element.asVariable(): VariableElement? = this as? VariableElement

fun Element.asExecutable(): ExecutableElement? = this as? ExecutableElement

val Element.getterName: String
    get() = "${if (asType().isBoolean) "is" else "get"}${name.toUpperCamel()}"

val Element.setterName: String
    get() = "set${name.toUpperCamel()}"

fun Element.hasAnnotation(klass: Class<out Annotation>?): Boolean = klass?.let { getAnnotation(it) } != null
