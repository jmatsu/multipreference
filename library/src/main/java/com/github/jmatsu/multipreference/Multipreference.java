package com.github.jmatsu.multipreference;

import android.content.Context;
import android.support.annotation.NonNull;

public class Multipreference {
    static final boolean DEBUG = false;

    @NonNull
    public static DataStore inMemory() {
        return new InMemoryDataStore();
    }

    @NonNull
    public static DataStore sharedPreferences(@NonNull Context context, @NonNull String name) {
        return new SharedPreferencesDataStore(context, name);
    }
}
