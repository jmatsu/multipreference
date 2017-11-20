package com.github.jmatsu.multipreference.processor.extension

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ParameterizedTypeNameSpek : Spek({
    describe("ParameterizedTypeName") {
        on("getSingleParameterizedTypeName") {
            it("should return a type like A<B>") {
                getSingleParameterizedTypeName<List<*>, String>().toString().shouldEqual("${List::class.java.canonicalName}<${String::class.java.canonicalName}>")
            }
        }

        on("getBiParameterizedTypeName") {
            it("should return a type like A<B<C>>") {
                getBiParameterizedTypeName<List<*>, List<*>, String>().toString().shouldEqual("${List::class.java.canonicalName}<${List::class.java.canonicalName}<${String::class.java.canonicalName}>>")
            }
        }
    }
})