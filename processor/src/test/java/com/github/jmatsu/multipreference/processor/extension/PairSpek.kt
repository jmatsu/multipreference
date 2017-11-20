package com.github.jmatsu.multipreference.processor.extension

import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object PairSpek : Spek({
    describe("Pair") {
        describe("traverse") {
            fun <A, B> pairOf(a: A?, b: B?): Pair<A?, B?> = a to b

            on("null and null") {
                it("should returns null") {
                    pairOf(null, null).traverse().shouldBeNull()
                }
            }

            on("non null and null") {
                it("should returns null") {
                    pairOf("a", null).traverse().shouldBeNull()
                }
            }

            on("null and non null") {
                it("should returns null") {
                    pairOf(null, "b").traverse().shouldBeNull()
                }
            }

            on("non null and non null") {
                it("should returns null") {
                    pairOf("a", "a").traverse().shouldEqual("a" to "a")
                }
            }
        }
    }
})