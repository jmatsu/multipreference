package com.github.jmatsu.multipreference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Preference {
    /**
     * @see Preference#name()
     */
    String DEFAULT_CLASS_NAME = "[class-name]";

    /**
     * Generated class's name so this is not a preference name.
     *
     * @return name you want use. Default is based on class name
     */
    String name() default DEFAULT_CLASS_NAME;
}
