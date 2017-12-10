package com.github.jmatsu.multipreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Just a wrapper of {@link SharedPreferences}
 */
final class SharedPreferencesDataStore implements DataStore {

    @NonNull
    private final Context context;

    @NonNull
    private final String name;

    @NonNull
    private final SharedPreferences sharedPreferences;

    @Nullable
    private SharedPreferences.Editor temporaryEditorForTransaction;

    private boolean isDestroyed;

    SharedPreferencesDataStore(@NonNull Context context, @NonNull String name) {
        this.context = context.getApplicationContext();
        this.name = name;
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
        getEditorToBeUpdated().putString(key, value).apply();
    }

    @Override
    public void setNonNullString(@NonNull String key, @NonNull String value) {
        getEditorToBeUpdated().putString(key, value).apply();
    }

    @Override
    public void setNullableStringSet(@NonNull String key, @Nullable Set<String> value) {
        getEditorToBeUpdated().putStringSet(key, value).apply();
    }

    @Override
    public void setNonNullStringSet(@NonNull String key, @NonNull Set<String> value) {
        getEditorToBeUpdated().putStringSet(key, value).apply();
    }

    @Override
    public void setInt(@NonNull String key, int value) {
        getEditorToBeUpdated().putInt(key, value).apply();
    }

    @Override
    public void setLong(@NonNull String key, long value) {
        getEditorToBeUpdated().putLong(key, value).apply();
    }

    @Override
    public void setFloat(@NonNull String key, float value) {
        getEditorToBeUpdated().putFloat(key, value).apply();
    }

    @Override
    public void setBoolean(@NonNull String key, boolean value) {
        getEditorToBeUpdated().putBoolean(key, value).apply();
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void beginTransaction() {
        checkNotDestroyed();

        if (isInTransaction()) {
            throw new RuntimeException("Multiple transactions are not allowed.");
        }

        temporaryEditorForTransaction = sharedPreferences.edit();
    }

    @Override
    public synchronized boolean finishTransaction(boolean commit) {
        checkNotDestroyed();

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
        checkNotDestroyed();

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
        if (isDestroyed) {
            return;
        }

        SharedPreferences.Editor editor = temporaryEditorForTransaction != null ? temporaryEditorForTransaction : sharedPreferences.edit();
        editor.clear().apply();
        temporaryEditorForTransaction = null;
    }

    @Override
    public void destroySelf() {
        if (isDestroyed) {
            return;
        }

        if (deleteSharedPreferences(context, name)) {
            isDestroyed = true;
        }
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @NonNull
    private SharedPreferences.Editor getEditorToBeUpdated() {
        checkNotDestroyed();
        return temporaryEditorForTransaction != null ? temporaryEditorForTransaction : sharedPreferences.edit();
    }

    private void checkNotDestroyed() {
        if (isDestroyed) {
            throw new RuntimeException("This operation is not allowed after destroy.");
        }
    }

    @Nullable
    private static File getSharedPreferencesFile(@NonNull Context context, @NonNull String name) {
        File file = getDataDir(context);

        if (file == null) {
            return null;
        }

        return new File(file, "/shared_prefs/" + name);
    }

    private static boolean deleteSharedPreferences(@NonNull Context context, @NonNull String name) {
        File file = getSharedPreferencesFile(context, name);
        return file == null || !file.exists() || file.delete();
    }

    @Nullable
    private static File getDataDir(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.getDataDir();
        } else {
            final String dataDir = context.getApplicationInfo().dataDir;
            return dataDir != null ? new File(dataDir) : null;
        }
    }
}
