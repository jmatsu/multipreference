## Multipreference

[ ![Download](https://api.bintray.com/packages/jmatsu/maven/multipreference/images/download.svg) ](https://bintray.com/jmatsu/maven/multipreference/_latestVersion) [![Build Status](https://travis-ci.org/jmatsu/multipreference.svg?branch=master)](https://travis-ci.org/jmatsu/multipreference)

Multipreference provides an annotation-based Key-Value store for Android development. 

- Reflection-free
- Support Map-based in-memory data store
- Support SharedPreferences-based data store
- Support flavor-based default value configurations

## Usage

You need to create a class which will have Key-Value definitions, and prepare each Key-Value definitions which are represented by fields.

```java
import com.github.jmatsu.multipreference.Key;
import com.github.jmatsu.multipreference.Preference;

@Preference
class KeyValueDefinitions {
    @Key
    static final int value = 0x01;
}
```

- The class must have `com.github.jmatsu.multipreference.Preference`.
- The class must not be *private*.
- Basically *private* fields are *NOT* allowed.

This library will generates a class `KeyValueDefinitionsPreference` and Key-Value API like below.

```java
private static final String VALUE = "value";

public int getValue() {
    return dataStore.getInt(VALUE, KeyValueDefinitions.value);
}

public void setValue(int value) {
    dataStore.setInt(VALUE, value);
}
```

You can use this class by calling `KeyValueDefinitionsPreference.inMemory()` or `KeyValueDefinitionsPreference.sharedPreference(context, preferencesName)`.

You can see more examples in *sample* module's source and testcases.

Especially for flavor-based configurations, you can check from:

- [Base configuration](https://github.com/jmatsu/multipreference/tree/master/sample/src/main/java/com/github/jmatsu/multipreference/sample/FlavorBasedConfig.java)
- [Debug configuration](https://github.com/jmatsu/multipreference/tree/master/sample/src/debug/java/com/github/jmatsu/multipreference/sample/DebugConfig.java) and [release configuration](https://github.com/jmatsu/multipreference/tree/master/sample/src/release/java/com/github/jmatsu/multipreference/sample/ReleaseConfig.java)
- How to use : [debug](https://github.com/jmatsu/multipreference/blob/master/sample/src/testDebug/java/com/github/jmatsu/multipreference/sample/FlavorBasedConfigSpek.kt) and [release](https://github.com/jmatsu/multipreference/blob/master/sample/src/testRelease/java/com/github/jmatsu/multipreference/sample/FlavorBasedConfigSpek.kt)

Please visit https://github.com/jmatsu/multipreference for the more details.
