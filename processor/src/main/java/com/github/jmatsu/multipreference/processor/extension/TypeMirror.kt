package com.github.jmatsu.multipreference.processor.extension

import com.github.jmatsu.multipreference.processor.elementUtils
import com.github.jmatsu.multipreference.processor.typeUtils
import com.squareup.javapoet.TypeName
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror

val TypeMirror.name: String
    get() {
        val toString: String = this.toString()
        val indexOfDot: Int = toString.lastIndexOf('.')
        return if (indexOfDot == -1) toString else toString.substring(indexOfDot + 1)
    }

val TypeMirror.isPrimitive: Boolean
    get() = TypeName.get(this).isPrimitive

val TypeMirror.isBoxedPrimitive: Boolean
    get() = TypeName.get(this).isBoxedPrimitive

inline fun <reified T> TypeMirror.isSameType(): Boolean = typeUtils.isSameType(this, typeUtils.erasure(elementUtils.getTypeElement(T::class.java.canonicalName).asType()))

val TypeMirror.isString: Boolean
    get() = isSameType<String>()

val TypeMirror.isBoolean: Boolean
    get() = isSameType<Boolean>()

val TypeMirror.isStringSet: Boolean
    get() = (this as? DeclaredType)?.let {
        typeUtils.erasure(it.asElement().asType()).isSameType<Set<*>>() && it.typeArguments.firstOrNull()?.isString == true
    } == true

fun TypeMirror.toNormalizeForAtomicReference(any: Any?): Any? = kind.toNormalizeForAtomicReference(any)