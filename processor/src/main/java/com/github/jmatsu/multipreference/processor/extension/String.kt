package com.github.jmatsu.multipreference.processor.extension

import com.google.common.base.CaseFormat

fun String.toLowerCamel(): String {
    return when {
        isLowerCamel -> this
        isUpperCamel -> toLowerCamel(CaseFormat.UPPER_CAMEL)
        isLowerUnderscore -> toLowerCamel(CaseFormat.LOWER_UNDERSCORE)
        isUpperUnderscore -> toLowerCamel(CaseFormat.UPPER_UNDERSCORE)
        else -> toLowerCamel(CaseFormat.UPPER_CAMEL) // cannot be determined
    }
}

fun String.toUpperCamel(): String {
    return when {
        isLowerCamel -> toUpperCamel(CaseFormat.LOWER_CAMEL)
        isUpperCamel -> this
        isLowerUnderscore -> toUpperCamel(CaseFormat.LOWER_UNDERSCORE)
        isUpperUnderscore -> toUpperCamel(CaseFormat.UPPER_UNDERSCORE)
        else -> toUpperCamel(CaseFormat.LOWER_CAMEL) // cannot be determined
    }
}

fun String.toLowerUnderscore(): String {
    return when {
        isLowerCamel -> toLowerUnderscore(CaseFormat.LOWER_CAMEL)
        isUpperCamel -> toLowerUnderscore(CaseFormat.UPPER_CAMEL)
        isLowerUnderscore -> this
        isUpperUnderscore -> toLowerUnderscore(CaseFormat.UPPER_UNDERSCORE)
        else -> toLowerUnderscore(CaseFormat.LOWER_CAMEL) // cannot be determined
    }
}

fun String.toUpperUnderscore(): String {
    return when {
        isLowerCamel -> toUpperUnderscore(CaseFormat.LOWER_CAMEL)
        isUpperCamel -> toUpperUnderscore(CaseFormat.UPPER_CAMEL)
        isLowerUnderscore -> toUpperUnderscore(CaseFormat.LOWER_UNDERSCORE)
        isUpperUnderscore -> this
        else -> toUpperUnderscore(CaseFormat.UPPER_CAMEL) // cannot be determined
    }
}

val String.isLowerCamel: Boolean
    get() = this == toUpperUnderscore(CaseFormat.LOWER_CAMEL).toLowerCamel(CaseFormat.UPPER_UNDERSCORE)

val String.isUpperCamel: Boolean
    get() = this == toLowerUnderscore(CaseFormat.UPPER_CAMEL).toUpperCamel(CaseFormat.LOWER_UNDERSCORE)

val String.isLowerUnderscore: Boolean
    get() = this == toUpperCamel(CaseFormat.LOWER_UNDERSCORE).toLowerUnderscore(CaseFormat.UPPER_CAMEL)

val String.isUpperUnderscore: Boolean
    get() = this == toLowerCamel(CaseFormat.UPPER_UNDERSCORE).toUpperUnderscore(CaseFormat.LOWER_CAMEL)


private fun String.toLowerCamel(base: CaseFormat): String = base.to(CaseFormat.LOWER_CAMEL, this)
private fun String.toUpperCamel(base: CaseFormat): String = base.to(CaseFormat.UPPER_CAMEL, this)
private fun String.toLowerUnderscore(base: CaseFormat): String = base.to(CaseFormat.LOWER_UNDERSCORE, this)
private fun String.toUpperUnderscore(base: CaseFormat): String = base.to(CaseFormat.UPPER_UNDERSCORE, this)