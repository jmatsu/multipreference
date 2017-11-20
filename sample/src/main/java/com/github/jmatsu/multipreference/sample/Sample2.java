package com.github.jmatsu.multipreference.sample;

import com.github.jmatsu.multipreference.Key;
import com.github.jmatsu.multipreference.Preference;

import java.util.HashSet;
import java.util.Set;

@Preference
public class Sample2 {

    @Key(placeholder = Key.GIVEN_PARAMETER)
    private int GIVEN_PARAMETER_NON_FINAL_NON_STATIC;

    @Key(placeholder = Key.GIVEN_PARAMETER)
    private static int GIVEN_PARAMETER_NON_FINAL;

    @Key(placeholder = Key.GIVEN_PARAMETER)
    private final int GIVEN_PARAMETER_NON_STATIC = 123;

    @Key(placeholder = Key.GIVEN_PARAMETER)
    private static final int GIVEN_PARAMETER = 123;

    @Key(cacheStrategy = Key.IMMEDIATE_CACHE)
    static final int IMMEDIATE_CACHE = 0x32;

    @Key(cacheStrategy = Key.LAZY_CACHE)
    static final int LAZY_CACHE = 64;

    @Key(name = "custom_CUSTOM_NAME")
    static final int CUSTOM_NAME = 123;

    @Key(cacheStrategy = Key.IMMEDIATE_CACHE, name = "custom_IMMEDIATE_CACHE_AND_CUSTOM_NAME")
    static final int IMMEDIATE_CACHE_AND_CUSTOM_NAME = 0x34;

    @Key(cacheStrategy = Key.LAZY_CACHE, name = "custom_LAZY_CACHE_AND_CUSTOM_NAME")
    static final Set<String> LAZY_CACHE_AND_CUSTOM_NAME = new HashSet<String>();

    @Key(placeholder = Key.GIVEN_PARAMETER, name = "custom_GIVEN_PARAMETER_AND_CUSTOM_NAME_NON_FINAL_NON_STATIC")
    private int GIVEN_PARAMETER_AND_CUSTOM_NAME_NON_FINAL_NON_STATIC;

    @Key(placeholder = Key.GIVEN_PARAMETER, name = "custom_GIVEN_PARAMETER_AND_CUSTOM_NAME_NON_FINAL")
    private static int GIVEN_PARAMETER_AND_CUSTOM_NAME_NON_FINAL;

    @Key(placeholder = Key.GIVEN_PARAMETER, name = "custom_GIVEN_PARAMETER_AND_CUSTOM_NAME_NON_STATIC")
    private final int GIVEN_PARAMETER_AND_CUSTOM_NAME_NON_STATIC = 0x34;

    @Key(placeholder = Key.GIVEN_PARAMETER, name = "custom_GIVEN_PARAMETER_AND_CUSTOM_NAME")
    private static final int GIVEN_PARAMETER_AND_CUSTOM_NAME = 0x34;

    @Key(cacheStrategy = Key.LAZY_CACHE, placeholder = Key.GIVEN_PARAMETER, name = "custom_LAZY_CACHE_AND_GIVEN_PARAMETER_AND_CUSTOM_NAME")
    static final Set<String> LAZY_CACHE_AND_GIVEN_PARAMETER_AND_CUSTOM_NAME = new HashSet<String>();
}
