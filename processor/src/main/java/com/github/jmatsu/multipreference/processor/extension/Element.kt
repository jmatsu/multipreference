package com.github.jmatsu.multipreference.processor.extension

import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.PackageElement
import javax.lang.model.type.TypeMirror

val Element.name: String
    get() = when (this) {
        is PackageElement -> "$qualifiedName"
        else -> "$simpleName"
    }

inline fun <reified T> Element.annotation(): T? where T : Annotation = getAnnotation(T::class.java)

val Element.getterName: String
    get() = "${if (asType().isBoolean) "is" else "get"}${parameterName.toUpperCamel()}"

val Element.setterName: String
    get() = "set${parameterName.toUpperCamel()}"

val Element.parameterName: String
    get() {
        return if (kind == ElementKind.METHOD) {
            name.toLowerCamel().replace("^get".toRegex(), "")
        } else {
            name.toLowerCamel()
        }
    }

fun Element.hasAnnotation(klass: Class<out Annotation>?): Boolean = klass?.let { getAnnotation(it) } != null

val Element.evaluateTypeMirror: TypeMirror
    get() {
        return (this as? ExecutableElement)?.returnType ?: asType()
    }

val Element.accessFormat: String
    get() = name.takeIf { kind != ElementKind.METHOD } ?: "$name()"