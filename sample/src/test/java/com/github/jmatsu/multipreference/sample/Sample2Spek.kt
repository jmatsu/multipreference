package com.github.jmatsu.multipreference.sample

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*

@RunWith(JUnitPlatform::class)
object Sample2Spek : Spek({
    describe("Sample2Preference") {
        val preference = Sample2Preference.inMemory()

        on("IMMEDIATE_CACHE") {
            it("should same as default") {
                preference.immediateCache.shouldEqual(Sample2.IMMEDIATE_CACHE)
            }

            it("should change ") {
                preference.immediateCache = 123123
                preference.immediateCache.shouldEqual(123123)
            }
        }

        on("LAZY_CACHE") {
            it("should same as default") {
                preference.lazyCache.shouldEqual(Sample2.LAZY_CACHE)
            }

            it("should change ") {
                preference.lazyCache = 234235215
                preference.lazyCache.shouldEqual(234235215)
            }
        }

        on("CUSTOM_NAME") {
            it("should same as default") {
                preference.customName.shouldEqual(Sample2.CUSTOM_NAME)
            }

            it("should change ") {
                preference.customName = -43
                preference.customName.shouldEqual(-43)
            }
        }

        on("IMMEDIATE_CACHE_AND_CUSTOM_NAME") {
            it("should same as default") {
                preference.immediateCacheAndCustomName.shouldEqual(Sample2.IMMEDIATE_CACHE_AND_CUSTOM_NAME)
            }

            it("should change ") {
                preference.immediateCacheAndCustomName = -43234
                preference.immediateCacheAndCustomName.shouldEqual(-43234)
            }
        }

        on("LAZY_CACHE_AND_CUSTOM_NAME") {
            it("should same as default") {
                preference.lazyCacheAndCustomName.shouldEqual(Sample2.LAZY_CACHE_AND_CUSTOM_NAME)
            }

            it("should change ") {
                preference.lazyCacheAndCustomName = Collections.singleton("foo")
                preference.lazyCacheAndCustomName.shouldEqual(Collections.singleton("foo"))
            }
        }
    }
})