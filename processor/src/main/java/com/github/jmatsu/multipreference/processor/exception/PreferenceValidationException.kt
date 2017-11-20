package com.github.jmatsu.multipreference.processor.exception

import javax.lang.model.element.Modifier

sealed class PreferenceValidationException(className: String, message: String) : Throwable("Preference '$className' : $message") {
    class InvalidModifier(className: String, modifier: Modifier) : PreferenceValidationException(className, "[$modifier] is not allowed")
}