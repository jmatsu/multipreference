package com.github.jmatsu.multipreference.processor.dsl

import com.squareup.javapoet.*
import javax.lang.model.type.TypeMirror

data class ModifiedType(val type: TypeName, val modifierSpec: ModifierSpec, private var nonNull: Boolean) {
    fun box(): ModifiedType = copy(type = type.box())
    fun unbox(): ModifiedType = copy(type = type.unbox())

    fun method(name: String, vararg parameters: ParameterSpec?, speckBuilder: CodeBlock.Builder.() -> Unit): MethodSpec {
        return MethodSpec.methodBuilder(name).returns(type).apply {

            addNullabilityIfPossible(type, nonNull)
            addModifiers(modifierSpec.modifiers.map { it.javaxModifier })
            addParameters(parameters.filterNotNull())
            addCode(CodeBlock.builder().apply(speckBuilder).build())
        }.build()
    }

    fun buildField(name: String, speckBuilder: FieldSpec.Builder.() -> Unit = {}): FieldSpec {
        return FieldSpec.builder(type, name).apply {
            addNullabilityIfPossible(type, nonNull)
            addModifiers(*modifierSpec.modifiers.map { it.javaxModifier }.toTypedArray())
        }.apply(speckBuilder).build()
    }

    fun parameter(name: String, speckBuilder: ParameterSpec.Builder.() -> Unit = {}): ParameterSpec {
        return ParameterSpec.builder(type, name).apply {
            addNullabilityIfPossible(type, nonNull)
            addModifiers(*modifierSpec.modifiers.map { it.javaxModifier }.toTypedArray())
        }.apply(speckBuilder).build()
    }
}

fun ModifierSpec.nonNullType(returnType: TypeName): ModifiedType = ModifiedType(returnType, this, true)
fun ModifierSpec.nonNullType(returnType: TypeMirror): ModifiedType = nonNullType(TypeName.get(returnType))
inline fun <reified T> ModifierSpec.nonNullType(): ModifiedType = nonNullType(TypeName.get(T::class.java))

fun ModifierSpec.nullType(returnType: TypeName): ModifiedType = ModifiedType(returnType, this, false)
fun ModifierSpec.nullType(returnType: TypeMirror): ModifiedType = nullType(TypeName.get(returnType))
inline fun <reified T> ModifierSpec.nullType() = nullType(TypeName.get(T::class.java))

fun ModifierSpec.type(returnType: TypeName, nonNull: Boolean): ModifiedType = ModifiedType(returnType, this, nonNull)
fun ModifierSpec.type(returnType: TypeMirror, nonNull: Boolean): ModifiedType = type(TypeName.get(returnType), nonNull)
inline fun <reified T> ModifierSpec.type(nonNull: Boolean) = type(TypeName.get(T::class.java), nonNull)

fun ModifierSpec.void(): ModifiedType = ModifiedType(TypeName.VOID, this, true)

fun nonNullType(returnType: TypeName): ModifiedType = default().nonNullType(returnType)
fun nonNullType(returnType: TypeMirror): ModifiedType = default().nonNullType(returnType)
inline fun <reified T> nonNullType(): ModifiedType = default().nonNullType<T>()

fun nullType(returnType: TypeName): ModifiedType = default().nullType(returnType)
fun nullType(returnType: TypeMirror): ModifiedType = default().nullType(returnType)
inline fun <reified T> nullType(): ModifiedType = default().nullType<T>()

fun type(returnType: TypeName, nonNull: Boolean): ModifiedType = default().type(returnType, nonNull)
fun type(returnType: TypeMirror, nonNull: Boolean): ModifiedType = default().type(returnType, nonNull)
inline fun <reified T> type(nonNull: Boolean): ModifiedType = default().type<T>(nonNull)