package com.github.jmatsu.multipreference.sample

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.*

object FlavorBasedConfigSpek : Spek({
    describe("Release flavored FlavorBasedConfig") {
        val preference = FlavorBasedConfigPreference.inMemory(ReleaseConfig())

        on("overriddenValue1") {
            it("should return release's value") {
                assertEquals(ReleaseConfig().overriddenValue1, preference.overriddenValue1)
            }
        }

        on("overriddenValue2") {
            it("should return release's value") {
                assertEquals(ReleaseConfig().overriddenValue2, preference.overriddenValue2)
            }
        }
    }
})