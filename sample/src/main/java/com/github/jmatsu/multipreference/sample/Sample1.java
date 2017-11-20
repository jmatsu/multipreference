package com.github.jmatsu.multipreference.sample;

import android.support.annotation.VisibleForTesting;

import com.github.jmatsu.multipreference.Key;
import com.github.jmatsu.multipreference.Preference;

import java.util.HashSet;
import java.util.Set;

@Preference
public class Sample1 {

    @Key
    static final int lower_underscore = 123;

    @Key
    static final int lowerCamel = 0x32;

    @Key
    static final int UPPER_UNDERSCORE = 64;

    @Key
    static final int UpperCamel = 64;

    @Key
    static final int INT_VALUE = 5;

    @Key
    static final float FLOAT_VALUE = 3f;

    @Key
    static final long LONG_VALUE = 3L;

    @Key
    static final boolean BOOLEAN_VALUE = true;

    @Key
    static final String STRING_VALUE = "string value";

    @Key
    static final String METHOD_BIND_VALUE = createValue();

    @Key
    static final Set<String> SET_VALUE = new HashSet<String>();

    @VisibleForTesting
    static String createValue() {
        return "createValue";
    }
}
