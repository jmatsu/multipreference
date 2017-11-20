package com.github.jmatsu.multipreference.processor.extension

import javax.lang.model.type.TypeKind

fun TypeKind.toNormalizeForAtomicReference(any: Any?): Any? = when (this) {
    TypeKind.BOOLEAN -> "${any ?: false}"
    TypeKind.INT -> "${any ?: 0}"
    TypeKind.LONG -> "${any ?: 0}".appendIfNotExists("L")
    TypeKind.FLOAT -> any?.let { "$it".appendIfNotExists("f") }
    else -> any
}

private fun String.appendIfNotExists(str: String) = if (endsWith(str)) this else "${this}$str"