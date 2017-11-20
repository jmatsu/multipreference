package com.github.jmatsu.multipreference.processor.dsl

import com.github.jmatsu.multipreference.processor.nonNullAnnotation
import com.github.jmatsu.multipreference.processor.nullableAnnotation
import com.squareup.javapoet.MethodSpec.Builder
import com.squareup.javapoet.TypeName

fun Builder.addNullabilityIfPossible(type: TypeName, nonNull: Boolean): Builder {
    if (type.isPrimitive) {
        return this
    }

    if (type == TypeName.VOID) {
        return this
    }

    return if (nonNull) {
        nonNullIfPossible()
    } else {
        nullableIfPossible()
    }
}

fun Builder.nonNullIfPossible(): Builder = apply {
    val annotation = nonNullAnnotation ?: return@apply
    addAnnotation(annotation)
}

fun Builder.nullableIfPossible(): Builder = apply {
    val annotation = nullableAnnotation ?: return@apply
    addAnnotation(annotation)
}
