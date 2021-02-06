package com.github.jmatsu.multipreference.processor.extension

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import javax.lang.model.type.TypeKind
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

object TypeKindSpek : Spek({
    describe("TypeKind") {
        given("LONG") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns 0L") {
                        assertEquals("0L", TypeKind.LONG.toNormalizeForAtomicReference(null))
                    }
                }

                on("a target is not null") {
                    it("has 'L' as suffix") {
                        assertEquals("12L", TypeKind.LONG.toNormalizeForAtomicReference(12))
                        assertEquals("-123L", TypeKind.LONG.toNormalizeForAtomicReference(-123))
                        assertEquals("12L", TypeKind.LONG.toNormalizeForAtomicReference(12L))
                        assertEquals("-123L", TypeKind.LONG.toNormalizeForAtomicReference(-123L))
                    }
                }
            }
        }

        given("BOOLEAN") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns false") {
                        assertEquals("false", TypeKind.BOOLEAN.toNormalizeForAtomicReference(null))
                    }
                }

                on("a target is not null") {
                    it("has no suffix") {
                        assertEquals("false", TypeKind.BOOLEAN.toNormalizeForAtomicReference(false))
                        assertEquals("true", TypeKind.BOOLEAN.toNormalizeForAtomicReference(true))
                    }
                }
            }
        }

        given("BOOLEAN") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns false") {
                        assertEquals("false", TypeKind.BOOLEAN.toNormalizeForAtomicReference(null))
                    }
                }

                on("a target is not null") {
                    it("has no suffix") {
                        assertEquals("false", TypeKind.BOOLEAN.toNormalizeForAtomicReference(false))
                        assertEquals("true", TypeKind.BOOLEAN.toNormalizeForAtomicReference(true))
                    }
                }
            }
        }

        given("INT") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns 0") {
                        assertEquals("0",  TypeKind.INT.toNormalizeForAtomicReference(null))
                    }
                }

                on("a target is not null") {
                    it("has no suffix") {
                        assertEquals("12", TypeKind.INT.toNormalizeForAtomicReference(12))
                        assertEquals("-123", TypeKind.INT.toNormalizeForAtomicReference(-123))
                    }
                }
            }
        }

        given("FLOAT") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns null") {
                        assertNull(TypeKind.FLOAT.toNormalizeForAtomicReference(null))
                    }
                }

                on("a target is not null") {
                    it("has 'f' as suffix") {
                        assertEquals("12f", TypeKind.FLOAT.toNormalizeForAtomicReference(12))
                        assertEquals("-123f", TypeKind.FLOAT.toNormalizeForAtomicReference(-123))
                        assertEquals("12.1f", TypeKind.FLOAT.toNormalizeForAtomicReference(12.1f))
                        assertEquals("-123.3f", TypeKind.FLOAT.toNormalizeForAtomicReference(-123.3f))
                    }
                }
            }
        }

        given("DECLARED") {
            context("toNormalizeForAtomicReference") {
                on("a target is null") {
                    it("returns null") {
                        assertNull(TypeKind.DECLARED.toNormalizeForAtomicReference(null))
                    }
                }

                on("a target is not null") {
                    it("returns self") {
                        assertEquals("abc", TypeKind.DECLARED.toNormalizeForAtomicReference("abc"))
                        val any = Any()
                        assertSame(any, TypeKind.DECLARED.toNormalizeForAtomicReference(any))
                    }
                }
            }
        }
    }
})