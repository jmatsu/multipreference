package com.github.jmatsu.multipreference.processor.dsl

import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import com.github.jmatsu.multipreference.processor.dsl.Modifier as DslModifier

class ModifierSpec(modifier: DslModifier? = null) {
    private val innerModifiers: MutableList<DslModifier> = arrayListOf()
    val modifiers: List<DslModifier>
        get() = innerModifiers

    init {
        if (modifier != null) {
            add(modifier)
        }
    }

    fun add(dslModifier: DslModifier): ModifierSpec = apply {
        innerModifiers.add(dslModifier)
    }

    fun private() = add(DslModifier.PRIVATE)
    fun default() = this
    fun public() = add(DslModifier.PUBLIC)
    fun protected() = add(DslModifier.PROTECTED)
    fun static() = add(DslModifier.STATIC)
    fun final() = add(DslModifier.FINAL)
    fun synchronized() = add(DslModifier.SYNCHRONIZED)
    fun volatile() = add(DslModifier.VOLATILE)
    fun transient() = add(DslModifier.TRANSIENT)
    fun native() = add(DslModifier.NATIVE)
    fun abstract() = add(DslModifier.ABSTRACT)

    fun constructor(vararg parameters: ParameterSpec, speckBuilder: CodeBlock.Builder.() -> Unit): MethodSpec {
        return MethodSpec.constructorBuilder().apply {
            addModifiers(modifiers.map { it.javaxModifier })
            addParameters(parameters.asIterable())
            addCode(CodeBlock.builder().apply(speckBuilder).build())
        }.build()
    }

    fun type(name: String, speckBuilder: TypeSpec.Builder.() -> Unit): TypeSpec {
        return TypeSpec.classBuilder(name).addModifiers(*modifiers.map { it.javaxModifier }.toTypedArray()).apply(speckBuilder).build()
    }
}

fun default(): ModifierSpec = ModifierSpec()
fun private(): ModifierSpec = ModifierSpec(DslModifier.PRIVATE)
fun public(): ModifierSpec = ModifierSpec(DslModifier.PUBLIC)
fun protected(): ModifierSpec = ModifierSpec(DslModifier.PROTECTED)
fun static(): ModifierSpec = ModifierSpec(DslModifier.STATIC)
fun final(): ModifierSpec = ModifierSpec(DslModifier.FINAL)
fun synchronized(): ModifierSpec = ModifierSpec(DslModifier.SYNCHRONIZED)
fun volatile(): ModifierSpec = ModifierSpec(DslModifier.VOLATILE)
fun transient(): ModifierSpec = ModifierSpec(DslModifier.TRANSIENT)
fun native(): ModifierSpec = ModifierSpec(DslModifier.NATIVE)
fun abstract(): ModifierSpec = ModifierSpec(DslModifier.ABSTRACT)