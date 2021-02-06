package com.github.jmatsu.multipreference.sample

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
object MethodBasedSpek : Spek({
    describe("MethodBasedPreference") {
        // to avoid a bug which kotlin class cannot access to abstract fun
        val definition: MethodBased = MethodBasedImpl()
        val preference = MethodBasedPreference.inMemory(definition)

        on("firstKey") {
            it("should same as default") {
                assertEquals(definition.firstKey(), preference.firstKey)
            }

            it("should change ") {
                preference.firstKey = "changed"
                assertEquals("changed", preference.firstKey)
            }
        }

        on("getSecondKey") {
            it("should same as default") {
                assertEquals(definition.secondKey, preference.secondKey)
            }

            it("should change ") {
                preference.secondKey = "changed"
                assertEquals("changed", preference.secondKey)
            }
        }

        on("thirdKey") {
            it("should same as default") {
                assertEquals(definition.thirdKey(), preference.thirdKey)
            }

            it("should change ") {
                preference.thirdKey = 123123123
                assertEquals(123123123, preference.thirdKey)
            }
        }
    }
}) {
}