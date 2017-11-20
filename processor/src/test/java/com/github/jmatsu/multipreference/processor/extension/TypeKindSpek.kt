package com.github.jmatsu.multipreference.processor.extension

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import javax.lang.model.type.TypeKind

object TypeKindSpek : Spek({
    describe("TypeKind") {
        given("LONG") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns 0L") {
                        TypeKind.LONG.toNormalizeForAtomicReference(null).shouldEqual("0L")
                    }
                }

                on("a target is not null") {
                    it("has 'L' as suffix") {
                        TypeKind.LONG.toNormalizeForAtomicReference(12).shouldEqual("12L")
                        TypeKind.LONG.toNormalizeForAtomicReference(-123).shouldEqual("-123L")
                        TypeKind.LONG.toNormalizeForAtomicReference(12L).shouldEqual("12L")
                        TypeKind.LONG.toNormalizeForAtomicReference(-123L).shouldEqual("-123L")
                    }
                }
            }
        }

        given("BOOLEAN") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns false") {
                        TypeKind.BOOLEAN.toNormalizeForAtomicReference(null).shouldEqual("false")
                    }
                }

                on("a target is not null") {
                    it("has no suffix") {
                        TypeKind.BOOLEAN.toNormalizeForAtomicReference(false).shouldEqual("false")
                        TypeKind.BOOLEAN.toNormalizeForAtomicReference(true).shouldEqual("true")
                    }
                }
            }
        }

        given("BOOLEAN") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns false") {
                        TypeKind.BOOLEAN.toNormalizeForAtomicReference(null).shouldEqual("false")
                    }
                }

                on("a target is not null") {
                    it("has no suffix") {
                        TypeKind.BOOLEAN.toNormalizeForAtomicReference(false).shouldEqual("false")
                        TypeKind.BOOLEAN.toNormalizeForAtomicReference(true).shouldEqual("true")
                    }
                }
            }
        }

        given("INT") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns 0") {
                        TypeKind.INT.toNormalizeForAtomicReference(null).shouldEqual("0")
                    }
                }

                on("a target is not null") {
                    it("has no suffix") {
                        TypeKind.INT.toNormalizeForAtomicReference(12).shouldEqual("12")
                        TypeKind.INT.toNormalizeForAtomicReference(-123).shouldEqual("-123")
                    }
                }
            }
        }

        given("FLOAT") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns null") {
                        TypeKind.FLOAT.toNormalizeForAtomicReference(null).shouldBeNull()
                    }
                }

                on("a target is not null") {
                    it("has 'f' as suffix") {
                        TypeKind.FLOAT.toNormalizeForAtomicReference(12).shouldEqual("12f")
                        TypeKind.FLOAT.toNormalizeForAtomicReference(-123).shouldEqual("-123f")
                        TypeKind.FLOAT.toNormalizeForAtomicReference(12.1f).shouldEqual("12.1f")
                        TypeKind.FLOAT.toNormalizeForAtomicReference(-123.3f).shouldEqual("-123.3f")
                    }
                }
            }
        }

        given("DECLARED") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns null") {
                        TypeKind.DECLARED.toNormalizeForAtomicReference(null).shouldBeNull()
                    }
                }

                on("a target is not null") {
                    it("returns self") {
                        TypeKind.DECLARED.toNormalizeForAtomicReference("abc").shouldEqual("abc")
                        val any = Any()
                        TypeKind.DECLARED.toNormalizeForAtomicReference(any).shouldBe(any)
                    }
                }
            }
        }
    }
})