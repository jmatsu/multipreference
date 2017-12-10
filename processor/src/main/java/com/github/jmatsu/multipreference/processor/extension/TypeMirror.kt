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
    get() = try {
        TypeName.get(this).isPrimitive
    } catch (e: Throwable) {
        false
    }

val TypeMirror.isBoxedPrimitive: Boolean
    get() = try {
        TypeName.get(this).isBoxedPrimitive
    } catch (e: Throwable) {
        false
    }

val TypeMirror.boxedTypeName: TypeName?
    get() = try {
        val typeName = TypeName.get(this)

        if (typeName.isPrimitive) {
            typeName.box()
        } else if (typeName.isBoxedPrimitive) {
            typeName
        } else {
            null
        }
    } catch (e: Throwable) {
        null
    }

inline fun <reified T> TypeMirror.isSameType(): Boolean {
    return boxedTypeName?.let {
        it == TypeName.get(elementUtils.getTypeElement(T::class.java.canonicalName).asType())
    } ?: run {
        typeUtils.isSameType(this, typeUtils.erasure(elementUtils.getTypeElement(T::class.java.canonicalName).asType()))
    }
}

val TypeMirror.isString: Boolean
    get() = isSameType<String>()

val TypeMirror.isBoolean: Boolean
    get() = isSameType<Boolean>()

val TypeMirror.isStringSet: Boolean
    get() = (this as? DeclaredType)?.let {
        typeUtils.erasure(it.asElement().asType()).isSameType<Set<*>>() && it.typeArguments.firstOrNull()?.isString == true
    } == true

fun TypeMirror.toNormalizeForAtomicReference(any: Any?): Any? = kind.toNormalizeForAtomicReference(any)