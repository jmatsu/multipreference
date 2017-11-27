package com.github.jmatsu.multipreference.processor.extension

fun <A, B> Pair<A?, B?>.traverse(): Pair<A, B>? where A : Any, B : Any {
    val first = first ?: return null
    val second = second ?: return null
    return first to second
}