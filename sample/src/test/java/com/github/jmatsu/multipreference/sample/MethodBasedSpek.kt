package com.github.jmatsu.multipreference.sample

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
object MethodBasedSpek : Spek({
    describe("MethodBasedPreference") {
        // to avoid a bug which kotlin class cannot access to abstract fun
        val definition: MethodBased = MethodBasedImpl()
        val preference = MethodBasedPreference.inMemory(definition)

        on("firstKey") {
            it("should same as default") {
                preference.firstKey.shouldEqual(definition.firstKey())
            }

            it("should change ") {
                preference.firstKey = "changed"
                preference.firstKey.shouldEqual("changed")
            }
        }

        on("getSecondKey") {
            it("should same as default") {
                preference.secondKey.shouldEqual(definition.secondKey)
            }

            it("should change ") {
                preference.secondKey = "changed"
                preference.secondKey.shouldEqual("changed")
            }
        }

        on("thirdKey") {
            it("should same as default") {
                preference.thirdKey.shouldEqual(definition.thirdKey())
            }

            it("should change ") {
                preference.thirdKey = 123123123
                preference.thirdKey.shouldEqual(123123123)
            }
        }
    }
}) {
}