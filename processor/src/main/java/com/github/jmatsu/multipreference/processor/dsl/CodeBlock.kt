package com.github.jmatsu.multipreference.processor.dsl

import com.squareup.javapoet.CodeBlock

fun CodeBlock.Builder.beginIf(controlFlow: String, vararg args: Any?, ifBody: CodeBlock.Builder.() -> Unit): CodeBlock.Builder = apply {
    beginControlFlow("if ($controlFlow)", *args)
    indent()
    with(this, ifBody)
    unindent()
}

fun CodeBlock.Builder.elseIf(controlFlow: String, vararg args: Any?, ifBody: CodeBlock.Builder.() -> Unit): CodeBlock.Builder = apply {
    nextControlFlow("else if ($controlFlow)", *args)
    indent()
    with(this, ifBody)
    unindent()
}

fun CodeBlock.Builder.`else`(ifBody: CodeBlock.Builder.() -> Unit): CodeBlock.Builder = apply {
    nextControlFlow("else")
    indent()
    with(this, ifBody)
    unindent()
}

fun CodeBlock.Builder.endIf() {
    endControlFlow()
}

fun CodeBlock.Builder.returns(format: String, vararg args: Any?) {
    addStatement("return $format", *args)
}