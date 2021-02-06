package com.github.jmatsu.multipreference.sample

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@RunWith(JUnitPlatform::class)
object Sample1Spek : Spek({
    describe("Sample1Preference") {
        val preference = Sample1Preference.inMemory()

        on("boolean properties") {
            it("should same as default") {
                assertEquals(Sample1.BOOLEAN_VALUE, preference.isBooleanValue)
            }

            it("should change ") {
                preference.isBooleanValue = false
                assertFalse { preference.isBooleanValue }
            }
        }

        on("float properties") {
            it("should same as default") {
                assertEquals(Sample1.FLOAT_VALUE, preference.floatValue)
            }

            it("should change ") {
                preference.floatValue = -4f
                assertEquals(-4f, preference.floatValue)
            }
        }

        on("integer properties") {
            it("should same as default") {
                assertEquals(Sample1.INT_VALUE, preference.intValue)
            }

            it("should change ") {
                preference.intValue = -43
                assertEquals(-43, preference.intValue)
            }
        }

        on("long properties") {
            it("should same as default") {
                assertEquals(Sample1.LONG_VALUE, preference.longValue)
            }

            it("should change ") {
                preference.longValue = -43
                assertEquals(-43L, preference.longValue)
            }
        }

        on("String properties") {
            it("should same as default") {
                assertEquals(Sample1.STRING_VALUE, preference.stringValue)
            }

            it("should change ") {
                preference.stringValue = "new value"
                assertEquals("new value", preference.stringValue)
            }
        }

        on("StringSet properties") {
            it("should same as default") {
                assertEquals(Sample1.SET_VALUE, preference.setValue)
            }

            it("should change ") {
                preference.setValue = Collections.singleton("new value")
                assertEquals(Collections.singleton("new value"), preference.setValue)
            }
        }

        on("Method binding") {
            it("should same as default") {
                assertEquals(Sample1.createValue(), preference.methodBindValue)
            }

            it("should change ") {
                preference.methodBindValue = "new bind value"
                assertEquals("new bind value", preference.methodBindValue)
            }
        }
    }
})