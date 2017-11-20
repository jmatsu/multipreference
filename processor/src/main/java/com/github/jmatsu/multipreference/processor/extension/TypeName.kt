package com.github.jmatsu.multipreference.processor.extension

import com.squareup.javapoet.TypeName

inline fun <reified T> getTypeName(): TypeName = TypeName.get(T::class.java)
