package com.github.jmatsu.multipreference.sample

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals

object FlavorBasedConfigSpek : Spek({
    describe("Debug flavored FlavorBasedConfig") {
        val preference = FlavorBasedConfigPreference.inMemory(DebugConfig())

        on("overriddenValue1") {
            it("should return debug's value") {
                assertEquals(DebugConfig().overriddenValue1, preference.overriddenValue1)
            }
        }

        on("overriddenValue2") {
            it("should return debug's value") {
                assertEquals(DebugConfig().overriddenValue2, preference.overriddenValue2)
            }
        }
    }
})