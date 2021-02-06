package com.github.jmatsu.multipreference.processor.extension

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

object ParameterizedTypeNameSpek : Spek({
    describe("ParameterizedTypeName") {
        on("getSingleParameterizedTypeName") {
            it("should return a type like A<B>") {
                assertEquals("${List::class.java.canonicalName}<${String::class.java.canonicalName}>", getSingleParameterizedTypeName<List<*>, String>().toString())
            }
        }

        on("getBiParameterizedTypeName") {
            it("should return a type like A<B<C>>") {
                assertEquals("${List::class.java.canonicalName}<${List::class.java.canonicalName}<${String::class.java.canonicalName}>>", getBiParameterizedTypeName<List<*>, List<*>, String>().toString())
            }
        }
    }
})