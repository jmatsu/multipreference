# Multipreference

[ ![Download](https://api.bintray.com/packages/jmatsu/maven/multipreference/images/download.svg) ](https://bintray.com/jmatsu/maven/multipreference/_latestVersion) ![bitrise badge](https://www.bitrise.io/app/fd08ee11f69067f7/status.svg?token=gymkU3UX_7B8exFVM8iR3A&branch=master)

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


## Download

The latest version : [ ![Download](https://api.bintray.com/packages/jmatsu/maven/multipreference/images/download.svg) ](https://bintray.com/jmatsu/maven/multipreference/_latestVersion)

You need to add the following to build.gradle of *android-application* module or *android-library* module.

```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/jmatsu/maven" 
    }
}

ext.multipreferenceVersion = '[latest_version]'

dependencies {
  implementation "com.github.jmatsu:multipreference:${multipreferenceVersion}"
  annotationProcessor "com.github.jmatsu:multipreference-processor:${multipreferenceVersion}"
}
```

## Issue Tracker

Use Github Issues of this repository. [multipreference/issues](https://github.com/jmatsu/multipreference/issues)

## LICENSE

```
 Copyright 2017 Jumpei Matsuda

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```