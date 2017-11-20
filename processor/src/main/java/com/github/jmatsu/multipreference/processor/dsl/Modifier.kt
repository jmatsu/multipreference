package com.github.jmatsu.multipreference.processor.dsl

import javax.lang.model.element.Modifier

sealed class Modifier(val javaxModifier: Modifier) {
    object PRIVATE : DslModifier(Modifier.PRIVATE)
    object PUBLIC : DslModifier(Modifier.PUBLIC)
    object PROTECTED : DslModifier(Modifier.PROTECTED)
    object STATIC : DslModifier(Modifier.STATIC)
    object FINAL : DslModifier(Modifier.FINAL)
    object SYNCHRONIZED : DslModifier(Modifier.SYNCHRONIZED)
    object VOLATILE : DslModifier(Modifier.VOLATILE)
    object TRANSIENT : DslModifier(Modifier.TRANSIENT)
    object NATIVE : DslModifier(Modifier.NATIVE)
    object ABSTRACT : DslModifier(Modifier.ABSTRACT)
}