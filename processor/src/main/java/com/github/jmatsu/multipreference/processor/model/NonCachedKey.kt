package com.github.jmatsu.multipreference.processor.model

import com.github.jmatsu.multipreference.processor.dsl.public
import com.github.jmatsu.multipreference.processor.dsl.type
import com.github.jmatsu.multipreference.processor.dsl.void
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import javax.lang.model.element.VariableElement

class NonCachedKey(keyAnnotation: KeyAnnotation, element: VariableElement) : Key(keyAnnotation, element) {
    override fun toFields(): Array<FieldSpec> = arrayOf()

    override fun toConstructorStatements(): Array<CodeBlock> = arrayOf()

    override fun toGetters(): Array<MethodSpec> = arrayOf(
            if (hasParameter) {
                public().type(valueType, nonNull).method(getterName, parameter) {
                    addStatement("return \$L", getterCodeOfDataStore)
                }
            } else {
                public().type(valueType, nonNull).method(getterName) {
                    addStatement("return \$L", getterCodeOfDataStore)
                }
            }
    )

    override fun toSetters(): Array<MethodSpec> = arrayOf(
            public().void().method(setterName, parameter) {
                addStatement("\$L", setterCodeOfDataStore)
            }
    )
}