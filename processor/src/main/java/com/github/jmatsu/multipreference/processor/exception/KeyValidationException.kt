package com.github.jmatsu.multipreference.processor.exception

import javax.lang.model.element.Modifier
import javax.lang.model.type.TypeMirror

sealed class KeyValidationException(parameterName: String, message: String) : Throwable("Key '$parameterName' : $message") {
    class NotFoundRequiredModifier(parameterName: String, modifier: Modifier) : KeyValidationException(parameterName, "[$modifier] is not found")
    class InvalidModifier(parameterName: String, modifier: Modifier) : KeyValidationException(parameterName, "[$modifier] is not allowed")
    class UnsupportedBoxedPrimitiveType(parameterName: String, type: TypeMirror) : KeyValidationException(parameterName, "Boxed primitive '[$type]' is not supported")
    class UnsupportedPrimitiveType(parameterName: String, type: TypeMirror) : KeyValidationException(parameterName, "Primitive '[$type]' is not supported")
    class UnsupportedType(parameterName: String, type: TypeMirror) : KeyValidationException(parameterName, "'[$type]' is not supported")
    sealed class NotAllowedCombinedParameter(parameterName: String, message: String) : KeyValidationException(parameterName, "[$message] is not allowed") {
        class ImmediateCacheAndGivenParameter(parameterName: String) : NotAllowedCombinedParameter(parameterName, "IMMEDIATE_CACHE and GIVEN_PARAMETER")
    }
}