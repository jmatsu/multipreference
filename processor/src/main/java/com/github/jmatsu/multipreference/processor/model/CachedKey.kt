package com.github.jmatsu.multipreference.processor.model

import com.github.jmatsu.multipreference.processor.dsl.*
import com.github.jmatsu.multipreference.processor.extension.*
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind

class CachedKey(keyAnnotation: KeyAnnotation, element: VariableElement) : Key(keyAnnotation, element) {
    private val cacheFieldName: String = "${parameterName}Ref"
    private val cacheReference: TypeName = toAtomicReferenceType()
    private val initialValue: Any? = valueType.toNormalizeForAtomicReference(null)

    override fun toFields(): Array<FieldSpec> = arrayOf(
            private().final().nonNullType(cacheReference).buildField(cacheFieldName)
    )

    override fun toConstructorStatements(): Array<CodeBlock> = arrayOf(
            CodeBlock.builder()
                    .addStatement("this.$cacheFieldName = new \$T(\$L)", cacheReference, if (immediateLoad) toGetterCodeOfDataStore(false) else "")
                    .build()
    )

    override fun toGetters(): Array<MethodSpec> {
        return arrayOf(
                public().type(valueType, nonNull).method(getterName, if (hasParameter) parameter else null) {
                    val tempVarName = "temp"

                    addStatement("\$T $tempVarName = $cacheFieldName.get()", valueType)

                    val initialValue = valueType.toNormalizeForAtomicReference(null)

                    beginIf("isEqual($tempVarName, \$L) || isEqual($tempVarName, \$L)", initialValue, defaultValue) {
                        addStatement("$tempVarName = \$L", getterCodeOfDataStore)
                        addStatement("$cacheFieldName.set($tempVarName)")
                    }.endIf()

                    returns(tempVarName)
                }
        )
    }

    override fun toSetters(): Array<MethodSpec> = arrayOf(
            public().void().method(setterName, parameter) {
                addStatement("$cacheFieldName.set(\$L)", initialValue)
                addStatement("\$L", setterCodeOfDataStore)
            }
    )

    private fun toAtomicReferenceType(): TypeName {
        return when (valueType.kind) {
            TypeKind.BOOLEAN -> getTypeName<AtomicBoolean>()
            TypeKind.INT -> getTypeName<AtomicInteger>()
            TypeKind.LONG -> getTypeName<AtomicLong>()
            TypeKind.FLOAT -> getSingleParameterizedTypeName<AtomicReference<*>, Float>()
            TypeKind.DECLARED -> when {
                valueType.isString -> getSingleParameterizedTypeName<AtomicReference<*>, String>()
                valueType.isStringSet -> getBiParameterizedTypeName<AtomicReference<*>, Set<*>, String>()
                else -> throw AssertionError("You must add a branch for this type : $valueType")
            }
            else -> throw AssertionError("You must add a branch for this type : $valueType whose kind is ${valueType.kind}")
        }
    }
}