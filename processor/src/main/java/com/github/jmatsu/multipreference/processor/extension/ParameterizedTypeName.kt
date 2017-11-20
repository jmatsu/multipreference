package com.github.jmatsu.multipreference.processor.extension

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName

inline fun <reified T, reified R> getSingleParameterizedTypeName(): ParameterizedTypeName = ParameterizedTypeName.get(T::class.java, R::class.java)

inline fun <reified T, reified R, reified U> getBiParameterizedTypeName(): ParameterizedTypeName = ParameterizedTypeName.get(ClassName.get(T::class.java), ParameterizedTypeName.get(R::class.java, U::class.java))