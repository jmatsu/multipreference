package com.github.jmatsu.multipreference.processor.model

import com.github.jmatsu.multipreference.DataStore
import com.github.jmatsu.multipreference.processor.dsl.default
import com.github.jmatsu.multipreference.processor.dsl.nonNullType
import com.github.jmatsu.multipreference.processor.dsl.private
import com.github.jmatsu.multipreference.processor.dsl.public
import com.github.jmatsu.multipreference.processor.elementUtils
import com.github.jmatsu.multipreference.processor.exception.KeyValidationException
import com.github.jmatsu.multipreference.processor.extension.annotation
import com.github.jmatsu.multipreference.processor.extension.name
import com.github.jmatsu.multipreference.processor.extension.toLowerCamel
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeSpec
import org.funktionale.either.Either
import org.funktionale.either.eitherTry
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

const val dataStoreVariableName: String = "dataStore"

class Preference(annotation: PreferenceAnnotation, private val typeElement: TypeElement) {
    private val className: String = annotation.actualClassValue(typeElement)
    private val keys: Array<Key> = typeElement.enclosedElements.mapNotNull { e ->
        e.annotation<KeyAnnotation>()?.let {
            Key.create(it, e)
        }
    }.toTypedArray()

    val packageName: String = elementUtils.getPackageOf(typeElement).name
    val elementName: String = typeElement.qualifiedName.toString()

    private val dataStoreParameter: ParameterSpec = nonNullType<DataStore>().parameter(dataStoreVariableName)
    private val keyValueDefParameter: ParameterSpec = nonNullType(typeElement.asType()).parameter("${typeElement.simpleName}".toLowerCamel())

    fun validate(): Either<Throwable, Unit> = eitherTry {
        when {
            Modifier.PRIVATE in typeElement.modifiers -> {
                throw KeyValidationException.InvalidModifier(typeElement.name, Modifier.PRIVATE)
            }
            else -> {
                // no-op
            }
        }
    }

    fun toTypeSpec(): Either<List<Throwable>, TypeSpec> {
        val throwables = arrayListOf<Throwable>()

        val typeSpec = public().type(className) {
            if (Modifier.FINAL in typeElement.modifiers) {
                addMethod(Utility.inMemoryDefaultArgFactoryMethod(packageName, className, keyValueDefParameter))
                addMethod(Utility.sharedPreferencesDefaultArgFactoryMethod(packageName, className, keyValueDefParameter))
            } else if (Modifier.ABSTRACT in typeElement.modifiers || typeElement.kind.isInterface) {
                addMethod(Utility.inMemoryFactoryMethod(packageName, className, keyValueDefParameter))
                addMethod(Utility.sharedPreferencesFactoryMethod(packageName, className, keyValueDefParameter))
            } else {
                addMethod(Utility.inMemoryDefaultArgFactoryMethod(packageName, className, keyValueDefParameter))
                addMethod(Utility.sharedPreferencesDefaultArgFactoryMethod(packageName, className, keyValueDefParameter))
                addMethod(Utility.inMemoryFactoryMethod(packageName, className, keyValueDefParameter))
                addMethod(Utility.sharedPreferencesFactoryMethod(packageName, className, keyValueDefParameter))
            }

            val constructorStatements: MutableList<CodeBlock> = arrayListOf()
            addField(private().final().nonNullType<DataStore>().buildField(dataStoreParameter.name))
            addField(private().final().nonNullType(typeElement.asType()).buildField(keyValueDefParameter.name))

            keys.forEach {
                with(it) {
                    it.validate().left().forEach {
                        throwables.add(it)
                    }

                    addField(toKeyDefinitionField())

                    addFields(toFields().asIterable())
                    addMethods(toGetters().asIterable())
                    addMethods(toSetters().asIterable())

                    constructorStatements.addAll(toConstructorStatements())
                }
            }

            addMethods(Utility.equalityMethods().asIterable())
            addMethods(Utility.transactionMethods().asIterable())
            addMethod(constructorSpec(constructorStatements))
        }

        if (throwables.isEmpty()) {
            return Either.right(typeSpec)
        } else {
            return Either.left(throwables)
        }
    }

    private fun PreferenceAnnotation.actualClassValue(element: Element): String =
            (name.takeUnless { it == PreferenceAnnotation.DEFAULT_CLASS_NAME } ?: "${element.name}Preference")

    private fun constructorSpec(loadCodeBlocks: List<CodeBlock>): MethodSpec = default().constructor(dataStoreParameter, keyValueDefParameter) {
        addStatement("this.\$L = \$L", dataStoreParameter.name, dataStoreParameter.name)
        addStatement("this.\$L = \$L", keyValueDefParameter.name, keyValueDefParameter.name)
        loadCodeBlocks.forEach { add(it) }
    }
}