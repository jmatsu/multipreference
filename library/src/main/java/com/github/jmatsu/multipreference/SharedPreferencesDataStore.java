package com.github.jmatsu.multipreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Just a wrapper of {@link SharedPreferences}
 */
final class SharedPreferencesDataStore implements DataStore {

    @NonNull
    private final SharedPreferences sharedPreferences;

    @Nullable
    private SharedPreferences.Editor temporaryEditorForTransaction;

    SharedPreferencesDataStore(@NonNull Context context, @NonNull String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    @Override
    public boolean contains(@NonNull String key) {
        return sharedPreferences.contains(key);
    }

    @Nullable
    @Override
    public String getNullableString(@NonNull String key, @Nullable String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @NonNull
    @Override
    public String getNonNullString(@NonNull String key, @NonNull String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Nullable
    @Override
    public Set<String> getNullableStringSet(@NonNull String key, @Nullable Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    @NonNull
    @Override
    public Set<String> getNonNullStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void setNullableString(@NonNull String key, @Nullable String value) {
        getEditorToBeUpdated().putString(key, value).commit();
    }

    @Override
    public void setNonNullString(@NonNull String key, @NonNull String value) {
        getEditorToBeUpdated().putString(key, value).commit();
    }

    @Override
    public void setNullableStringSet(@NonNull String key, @Nullable Set<String> value) {
        getEditorToBeUpdated().putStringSet(key, value).commit();
    }

    @Override
    public void setNonNullStringSet(@NonNull String key, @NonNull Set<String> value) {
        getEditorToBeUpdated().putStringSet(key, value).commit();
    }

    @Override
    public void setInt(@NonNull String key, int value) {
        getEditorToBeUpdated().putInt(key, value).commit();
    }

    @Override
    public void setLong(@NonNull String key, long value) {
        getEditorToBeUpdated().putLong(key, value).commit();
    }

    @Override
    public void setFloat(@NonNull String key, float value) {
        getEditorToBeUpdated().putFloat(key, value).commit();
    }

    @Override
    public void setBoolean(@NonNull String key, boolean value) {
        getEditorToBeUpdated().putBoolean(key, value).commit();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void beginTransaction() {
        if (isInTransaction()) {
            throw new RuntimeException("Multiple transactions are not allowed.");
        }

        temporaryEditorForTransaction = sharedPreferences.edit();
    }

    @Override
    public synchronized boolean finishTransaction(boolean commit) {
        if (!isInTransaction()) {
            return false;
        }

        if (temporaryEditorForTransaction == null) {
            return false;
        }

        final boolean succeeded;

        if (commit) {
            succeeded = temporaryEditorForTransaction.commit();
        } else {
            temporaryEditorForTransaction.apply();
            succeeded = true;
        }

        temporaryEditorForTransaction = null;
        return succeeded;
    }

    @Override
    public synchronized void cancelTransaction() {
        if (!isInTransaction()) {
            return; // just finish
        }

        temporaryEditorForTransaction = null;

        if (Multipreference.DEBUG) {
            if (isInTransaction()) {
                throw new AssertionError("Transactions should be finished if canceled.");
            }
        }
    }

    @Override
    public boolean isInTransaction() {
        return temporaryEditorForTransaction != null;
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = temporaryEditorForTransaction != null ? temporaryEditorForTransaction : sharedPreferences.edit();
        editor.clear().commit();
        temporaryEditorForTransaction = null;
    }

    @NonNull
    private SharedPreferences.Editor getEditorToBeUpdated() {
        return temporaryEditorForTransaction != null ? temporaryEditorForTransaction : sharedPreferences.edit();
    }
}
