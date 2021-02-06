package com.github.jmatsu.multipreference.processor.util

private val nonNullAnnotationNames: Array<String> = arrayOf("androidx.annotation.NonNull")
private val nullableAnnotationNames: Array<String> = arrayOf("androidx.annotation.Nullable")

@Suppress("unchecked_cast")
fun getNonNullAnnotation(): Class<out Annotation>? {
    return try {
        nonNullAnnotationNames.map { Class.forName(it) as Class<Annotation> }.firstOrNull()
    } catch (e: Throwable) {
        null
    }
}

@Suppress("unchecked_cast")
fun getNullableAnnotation(): Class<out Annotation>? {
    return try {
        nullableAnnotationNames.map { Class.forName(it) as Class<Annotation> }.firstOrNull()
    } catch (e: Throwable) {
        null
    }
}