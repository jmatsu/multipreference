package com.github.jmatsu.multipreference.processor.model

import android.content.Context
import com.github.jmatsu.multipreference.Multipreference
import com.github.jmatsu.multipreference.processor.dsl.*
import com.github.jmatsu.multipreference.processor.extension.getSingleParameterizedTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec

object Utility {

    fun equalityMethods(): Array<MethodSpec> = arrayOf(
            objectEqualityMethod<Int>(),
            objectEqualityMethod<Float>(),
            objectEqualityMethod<Long>(),
            objectEqualityMethod<Boolean>(),
            objectEqualityMethod<String>(),
            setEqualityMethod<String>()
    )

    fun inMemoryFactoryMethod(packageName: String, className: String, keyValueDefParameter: ParameterSpec): MethodSpec {
        return public().static().nonNullType(ClassName.get(packageName, className)).method("inMemory", keyValueDefParameter) {
            returns("new $className(\$T.inMemory(), \$L)", Multipreference::class.java, keyValueDefParameter.name)
        }
    }

    fun inMemoryDefaultArgFactoryMethod(packageName: String, className: String, keyValueDefParameter: ParameterSpec): MethodSpec {
        return public().static().nonNullType(ClassName.get(packageName, className)).method("inMemory") {
            returns("new $className(\$T.inMemory(), new \$T())", Multipreference::class.java, keyValueDefParameter.type)
        }
    }

    fun sharedPreferencesFactoryMethod(packageName: String, className: String, keyValueDefParameter: ParameterSpec): MethodSpec {
        return public().static().nonNullType(ClassName.get(packageName, className)).method("sharedPreferences",
                default().nonNullType<Context>().parameter("context"),
                default().nonNullType<String>().parameter("name"),
                keyValueDefParameter
        ) {
            returns("new $className(\$T.sharedPreferences(context, name), \$L)", Multipreference::class.java, keyValueDefParameter.name)
        }
    }

    fun sharedPreferencesDefaultArgFactoryMethod(packageName: String, className: String, keyValueDefParameter: ParameterSpec): MethodSpec {
        return public().static().nonNullType(ClassName.get(packageName, className)).method("sharedPreferences",
                default().nonNullType<Context>().parameter("context"),
                default().nonNullType<String>().parameter("name")
        ) {
            returns("new $className(\$T.sharedPreferences(context, name), new \$T())", Multipreference::class.java, keyValueDefParameter.type)
        }
    }

    fun transactionMethods(): Array<MethodSpec> = arrayOf(
            beginTransactionMethod(),
            cancelTransactionMethod(),
            finishTransactionMethod()
    )

    private fun beginTransactionMethod(): MethodSpec {
        return public().void().method("beginTransaction") {
            addStatement("dataStore.beginTransaction()")
        }
    }

    private fun cancelTransactionMethod(): MethodSpec {
        return public().void().method("cancelTransaction") {
            addStatement("dataStore.cancelTransaction()")
        }
    }

    private fun finishTransactionMethod(): MethodSpec {
        return public().nonNullType<Boolean>().method("finishTransaction", nonNullType<Boolean>().parameter("commit")) {
            returns("dataStore.finishTransaction(commit)")
        }
    }

    private inline fun <reified T> objectEqualityMethod(): MethodSpec {
        return private().static().nonNullType<Boolean>().unbox().method("isEqual",
                default().nullType<T>().parameter("lhs"),
                default().nullType<T>().parameter("rhs")
        ) {
            returns("lhs != null ? lhs.equals(rhs) : rhs == null")
        }
    }

    private inline fun <reified T> setEqualityMethod(): MethodSpec {
        val typeName = getSingleParameterizedTypeName<Set<*>, T>()

        return private().static().nonNullType<Boolean>().unbox().method("isEqual",
                default().nullType(typeName).parameter("lhs"),
                default().nullType(typeName).parameter("rhs")
        ) {
            beginIf("lhs == rhs") {
                returns("true")
            }.elseIf("lhs != null && rhs != null") {
                beginIf("lhs.size() != rhs.size()") {
                    returns("false")
                }.endIf()

                add("for (\$T e: lhs) {\n", T::class.java)
                indent()
                beginIf("!rhs.contains(e)") {
                    returns("false")
                }.endIf()
                unindent()
                add("}\n")

                returns("true")
            }.`else` {
                returns("false")
            }.endIf()
        }
    }
}