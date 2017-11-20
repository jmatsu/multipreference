package com.github.jmatsu.multipreference.sample

import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object FlavorBasedConfigSpek : Spek({
    describe("Debug flavored FlavorBasedConfig") {
        val preference = FlavorBasedConfigPreference.inMemory(DebugConfig())

        on("overriddenValue1") {
            it("should return debug's value") {
                preference.overriddenValue1.shouldEqualTo(DebugConfig().overriddenValue1)
            }
        }

        on("overriddenValue2") {
            it("should return debug's value") {
                preference.overriddenValue2?.shouldEqualTo(DebugConfig().overriddenValue2)
            }
        }
    }
})