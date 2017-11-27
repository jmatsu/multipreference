package com.github.jmatsu.multipreference.processor.model

import com.github.jmatsu.multipreference.Key
import com.github.jmatsu.multipreference.processor.KeyModel
import com.github.jmatsu.multipreference.processor.dsl.default
import com.github.jmatsu.multipreference.processor.dsl.nonNullType
import com.github.jmatsu.multipreference.processor.dsl.private
import com.github.jmatsu.multipreference.processor.dsl.type
import com.github.jmatsu.multipreference.processor.exception.KeyValidationException
import com.github.jmatsu.multipreference.processor.exception.OutOfNameStrategyException
import com.github.jmatsu.multipreference.processor.extension.*
import com.github.jmatsu.multipreference.processor.nonNullAnnotation
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import org.funktionale.either.Either
import org.funktionale.either.eitherTry
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeKind.*
import javax.lang.model.type.TypeMirror

abstract class Key(keyAnnotation: KeyAnnotation, private val element: Element) {
    companion object {
        fun create(keyAnnotation: KeyAnnotation, element: Element): KeyModel {
            val shouldCache = keyAnnotation.cacheStrategy in arrayOf(KeyAnnotation.LAZY_CACHE, KeyAnnotation.IMMEDIATE_CACHE)

            if (shouldCache) {
                return CachedKey(keyAnnotation, element)
            } else {
                return NonCachedKey(keyAnnotation, element)
            }
        }
    }


    protected val parameterName: String = element.parameterName

    private val keyDefinitionFieldName: String = parameterName.toUpperUnderscore()
    private val keyValue: String = keyAnnotation.actualKeyValue(element)
    protected val valueType: TypeMirror = element.evaluateTypeMirror
    protected val immediateLoad: Boolean = keyAnnotation.cacheStrategy == KeyAnnotation.IMMEDIATE_CACHE
    private val lazyCache: Boolean = keyAnnotation.cacheStrategy == KeyAnnotation.LAZY_CACHE
    protected val nonNull: Boolean = element.hasAnnotation(nonNullAnnotation)

    protected val getterName: String = element.getterName
    protected val setterName: String = element.setterName
    protected val hasParameter: Boolean = keyAnnotation.placeholder == KeyAnnotation.GIVEN_PARAMETER

    protected val defaultValue: Any = {
        val kvDefinitionName = "${element.enclosingElement.simpleName}"

        val accessor = if (Modifier.STATIC in element.modifiers) {
            kvDefinitionName
        } else {
            kvDefinitionName.toLowerCamel()
        }

        "$accessor.${element.accessFormat}"
    }()

    protected val parameter: ParameterSpec by lazy {
        default().type(valueType, nonNull).parameter(parameterName)
    }

    protected val getterCodeOfDataStore: CodeBlock by lazy {
        toGetterCodeOfDataStore()
    }

    protected val setterCodeOfDataStore: CodeBlock by lazy {
        toSetterCodeOfDataStore()
    }

    fun validate(): Either<Throwable, Unit> = eitherTry {
        when {
            Modifier.PRIVATE in element.modifiers && !hasParameter -> {
                throw KeyValidationException.InvalidModifier(parameterName, Modifier.PRIVATE)
            }
            Modifier.PRIVATE in element.modifiers && hasParameter && lazyCache -> {
                throw KeyValidationException.InvalidModifier(parameterName, Modifier.PRIVATE)
            }
            immediateLoad && hasParameter -> {
                throw KeyValidationException.NotAllowedCombinedParameter.ImmediateCacheAndGivenParameter(parameterName)
            }
            valueType.isBoxedPrimitive -> {
                throw KeyValidationException.UnsupportedBoxedPrimitiveType(parameterName, valueType)
            }
            valueType.isPrimitive -> {
                if (valueType.kind !in arrayOf(BOOLEAN, INT, LONG, FLOAT)) {
                    throw KeyValidationException.UnsupportedPrimitiveType(parameterName, valueType)
                }
            }
            else -> {
                if (!valueType.isString && !valueType.isStringSet) {
                    throw KeyValidationException.UnsupportedType(parameterName, valueType)
                }
            }
        }
    }

    fun toKeyDefinitionField(): FieldSpec = private().static().final().nonNullType<String>().buildField(keyDefinitionFieldName) {
        initializer("\$S", keyValue)
    }

    abstract fun toFields(): Array<FieldSpec>

    abstract fun toConstructorStatements(): Array<CodeBlock>

    abstract fun toGetters(): Array<MethodSpec>

    abstract fun toSetters(): Array<MethodSpec>

    private fun KeyAnnotation.actualKeyValue(element: Element): String =
            (name.takeUnless { it == KeyAnnotation.DEFAULT_NAME } ?: element.parameterName).onStrategy(nameStrategy)

    protected fun toGetterCodeOfDataStore(useParameter: Boolean = true): CodeBlock {
        val defaultValue = if (hasParameter and useParameter) parameterName else defaultValue

        val methodName = "get" + when {
            valueType.kind == BOOLEAN -> "Boolean"
            valueType.kind == INT -> "Int"
            valueType.kind == LONG -> "Long"
            valueType.kind == FLOAT -> "Float"
            valueType.isString and nonNull -> "NonNullString"
            valueType.isString and !nonNull -> "NullableString"
            valueType.isStringSet and nonNull -> "NonNullStringSet"
            valueType.isStringSet and !nonNull -> "NullableStringSet"
            else -> TODO("$this cannot be processed. This exception is only for library developers.")
        }

        return CodeBlock.of("$dataStoreVariableName.$methodName(\$L, \$L)", keyDefinitionFieldName, defaultValue)
    }

    private fun toSetterCodeOfDataStore(): CodeBlock {
        val methodName = "set" + when {
            valueType.kind == BOOLEAN -> "Boolean"
            valueType.kind == INT -> "Int"
            valueType.kind == LONG -> "Long"
            valueType.kind == FLOAT -> "Float"
            valueType.isString and nonNull -> "NonNullString"
            valueType.isString and !nonNull -> "NullableString"
            valueType.isStringSet and nonNull -> "NonNullStringSet"
            valueType.isStringSet and !nonNull -> "NullableStringSet"
            else -> TODO("$this cannot be processed. This exception is only for library developers.")
        }

        return CodeBlock.of("$dataStoreVariableName.$methodName(\$L, \$L)", keyDefinitionFieldName, parameterName)
    }

    private fun String.onStrategy(@Key.NameStrategy strategy: Int): String = when (strategy) {
        KeyAnnotation.LOWER_CAMEL -> toLowerCamel()
        KeyAnnotation.UPPER_CAMEL -> toUpperCamel()
        KeyAnnotation.LOWER_UNDERSCORE -> toLowerUnderscore()
        KeyAnnotation.UPPER_UNDERSCORE -> toUpperUnderscore()
        KeyAnnotation.NONE -> this
        else -> throw OutOfNameStrategyException(strategy)
    }
}