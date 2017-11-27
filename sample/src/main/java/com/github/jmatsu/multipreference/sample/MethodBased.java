package com.github.jmatsu.multipreference.sample;

import com.github.jmatsu.multipreference.Key;
import com.github.jmatsu.multipreference.Preference;

@Preference
public abstract class MethodBased {

    @Key
    abstract String firstKey();

    @Key
    abstract String getSecondKey();

    @Key
    int thirdKey() {
        return 100;
    }
}
