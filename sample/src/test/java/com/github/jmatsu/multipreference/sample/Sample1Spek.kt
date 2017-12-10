package com.github.jmatsu.multipreference.sample

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*

@RunWith(JUnitPlatform::class)
object Sample1Spek : Spek({
    describe("Sample1Preference") {
        val preference = Sample1Preference.inMemory()

        on("boolean properties") {
            it("should same as default") {
                preference.isBooleanValue.shouldEqual(Sample1.BOOLEAN_VALUE)
            }

            it("should change ") {
                preference.isBooleanValue = false
                preference.isBooleanValue.shouldBeFalse()
            }
        }

        on("float properties") {
            it("should same as default") {
                preference.floatValue.shouldEqual(Sample1.FLOAT_VALUE)
            }

            it("should change ") {
                preference.floatValue = -4f
                preference.floatValue.shouldEqual(-4f)
            }
        }

        on("integer properties") {
            it("should same as default") {
                preference.intValue.shouldEqual(Sample1.INT_VALUE)
            }

            it("should change ") {
                preference.intValue = -43
                preference.intValue.shouldEqual(-43)
            }
        }

        on("long properties") {
            it("should same as default") {
                preference.longValue.shouldEqual(Sample1.LONG_VALUE)
            }

            it("should change ") {
                preference.longValue = -43
                preference.longValue.shouldEqual(-43L)
            }
        }

        on("String properties") {
            it("should same as default") {
                preference.stringValue.shouldEqual(Sample1.STRING_VALUE)
            }

            it("should change ") {
                preference.stringValue = "new value"
                preference.stringValue.shouldEqual("new value")
            }
        }

        on("StringSet properties") {
            it("should same as default") {
                preference.setValue.shouldEqual(Sample1.SET_VALUE)
            }

            it("should change ") {
                preference.setValue = Collections.singleton("new value")
                preference.setValue.shouldEqual(Collections.singleton("new value"))
            }
        }

        on("Method binding") {
            it("should same as default") {
                preference.methodBindValue.shouldEqual(Sample1.createValue())
            }

            it("should change ") {
                preference.methodBindValue = "new bind value"
                preference.methodBindValue.shouldEqual("new bind value")
            }
        }
    }
})