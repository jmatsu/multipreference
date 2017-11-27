package com.github.jmatsu.multipreference;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Key {
    /**
     * @see Key#name()
     */
    String DEFAULT_NAME = "[element-name]";

    /**
     * If you specify this, the value will be used for a key of a store.
     * Otherwise, an element name which this annotates will be converted based on {@link Key#nameStrategy()} and will be used for a key of a store.
     *
     * @return a name which you want to use or {@link Key#DEFAULT_NAME}
     */
    String name() default DEFAULT_NAME;

    @CacheStrategy int cacheStrategy() default NO_CACHE;

    /**
     * Specify a name strategy to convert a key field name.
     * This won't be used unless {@link Key#name()} equals {@link Key#DEFAULT_NAME}
     *
     * @return see {@link NameStrategy}
     */
    @NameStrategy int nameStrategy() default NONE;

    @Placeholder int placeholder() default DEFAULT_PLACEHOLDER;

    /**
     * Always read data from a data store
     */
    int NO_CACHE = 0;

    /**
     * Do cache data from a data store lazy
     */
    int LAZY_CACHE = 1;

    /**
     * Do cache data from a data store in constructor first
     */
    int IMMEDIATE_CACHE = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NO_CACHE, LAZY_CACHE, IMMEDIATE_CACHE})
    @interface CacheStrategy {
    }

    /**
     * Use a field name as it is
     */
    int NONE = 0;

    /**
     * Convert a field name to lower camel
     * e.g. lowerCamel
     */
    int LOWER_CAMEL = 1;

    /**
     * Convert a field name to upper camel
     * e.g. UpperCamel
     */
    int UPPER_CAMEL = 2;

    /**
     * Convert a field name to lower underscore
     * e.g. lower_underscore
     */
    int LOWER_UNDERSCORE = 3;

    /**
     * Convert a field name to upper underscore
     * e.g. UPPER_UNDERSCORE
     */
    int UPPER_UNDERSCORE = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NONE, LOWER_CAMEL, UPPER_CAMEL, LOWER_UNDERSCORE, UPPER_UNDERSCORE})
    @interface NameStrategy {
    }

    /**
     * Use a value of a field definition as a placeholder
     */
    int DEFAULT_PLACEHOLDER = 0;

    /**
     * Use a given parameter as a placeholder
     */
    int GIVEN_PARAMETER = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DEFAULT_PLACEHOLDER, GIVEN_PARAMETER})
    @interface Placeholder {
    }
}
