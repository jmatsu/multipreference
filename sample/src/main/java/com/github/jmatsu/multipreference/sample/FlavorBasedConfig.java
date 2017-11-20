package com.github.jmatsu.multipreference.sample;

import com.github.jmatsu.multipreference.Key;
import com.github.jmatsu.multipreference.Preference;

@Preference
abstract class FlavorBasedConfig {

    @Key
    final int overriddenValue1;

    @Key
    final String overriddenValue2;

    protected FlavorBasedConfig(int overriddenValue1, String overriddenValue2) {
        this.overriddenValue1 = overriddenValue1;
        this.overriddenValue2 = overriddenValue2;
    }
}
