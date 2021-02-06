package com.github.jmatsu.multipreference.sample

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*
import kotlin.test.assertEquals

@RunWith(JUnitPlatform::class)
object Sample2Spek : Spek({
    describe("Sample2Preference") {
        val preference = Sample2Preference.inMemory()

        on("IMMEDIATE_CACHE") {
            it("should same as default") {
                assertEquals(Sample2.IMMEDIATE_CACHE, preference.immediateCache)
            }

            it("should change ") {
                preference.immediateCache = 123123
                assertEquals(123123, preference.immediateCache)
            }
        }

        on("LAZY_CACHE") {
            it("should same as default") {
                assertEquals(Sample2.LAZY_CACHE, preference.lazyCache)
            }

            it("should change ") {
                preference.lazyCache = 234235215
                assertEquals(234235215, preference.lazyCache)
            }
        }

        on("CUSTOM_NAME") {
            it("should same as default") {
                assertEquals(Sample2.CUSTOM_NAME, preference.customName)
            }

            it("should change ") {
                preference.customName = -43
                assertEquals(-43, preference.customName)
            }
        }

        on("IMMEDIATE_CACHE_AND_CUSTOM_NAME") {
            it("should same as default") {
                assertEquals(Sample2.IMMEDIATE_CACHE_AND_CUSTOM_NAME, preference.immediateCacheAndCustomName)
            }

            it("should change ") {
                preference.immediateCacheAndCustomName = -43234
                assertEquals(-43234, preference.immediateCacheAndCustomName)
            }
        }

        on("LAZY_CACHE_AND_CUSTOM_NAME") {
            it("should same as default") {
                assertEquals(Sample2.LAZY_CACHE_AND_CUSTOM_NAME, preference.lazyCacheAndCustomName)
            }

            it("should change ") {
                preference.lazyCacheAndCustomName = Collections.singleton("foo")
                assertEquals(Collections.singleton("foo"), preference.lazyCacheAndCustomName)
            }
        }
    }
})