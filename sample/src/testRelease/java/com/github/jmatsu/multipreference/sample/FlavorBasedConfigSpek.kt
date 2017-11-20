package com.github.jmatsu.multipreference.sample

import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object FlavorBasedConfigSpek : Spek({
    describe("Release flavored FlavorBasedConfig") {
        val preference = FlavorBasedConfigPreference.inMemory(ReleaseConfig())

        on("overriddenValue1") {
            it("should return release's value") {
                preference.overriddenValue1.shouldEqualTo(ReleaseConfig().overriddenValue1)
            }
        }

        on("overriddenValue2") {
            it("should return release's value") {
                preference.overriddenValue2?.shouldEqualTo(ReleaseConfig().overriddenValue2)
            }
        }
    }
})