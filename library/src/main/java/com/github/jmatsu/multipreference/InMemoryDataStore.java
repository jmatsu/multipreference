package com.github.jmatsu.multipreference;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory data store. Does not support any async save/load so commit action is same as apply.
 */
final class InMemoryDataStore implements DataStore {

    /**
     * Return a value of map. This imitates {@link android.content.SharedPreferences}'s behaviors.
     * That's why this doesn't not use containsKey to check.
     *
     * @param map          Map object
     * @param key          a name of key
     * @param defaultValue a value to be returned if an associated value is missing or null.
     * @param <T>          returned type.
     * @return If a non-null value is found, then return it. Otherwise, returns defaultValue.
     */
    @SuppressWarnings("unchecked")
    private static <T> T getOrDefault(@NonNull Map<String, ?> map, @NonNull String key, @Nullable T defaultValue) {
        T value = (T) map.get(key);

        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    @NonNull
    private final Map<String, Object> map;

    @Nullable
    private Map<String, Object> temporaryMapForTransaction;

    InMemoryDataStore() {
        map = new ConcurrentHashMap<String, Object>();
    }

    @NonNull
    @Override
    public Map<String, ?> getAll() {
        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean contains(@NonNull String key) {
        return map.containsKey(key);
    }

    @Nullable
    @Override
    public String getNullableString(@NonNull String key, @Nullable String defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @NonNull
    @Override
    public String getNonNullString(@NonNull String key, @NonNull String defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @Nullable
    @Override
    public Set<String> getNullableStringSet(@NonNull String key, @Nullable Set<String> defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @NonNull
    @Override
    public Set<String> getNonNullStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return getOrDefault(map, key, defaultValue);
    }

    @Override
    public void setNullableString(@NonNull String key, @Nullable String value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setNonNullString(@NonNull String key, @NonNull String value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setNullableStringSet(@NonNull String key, @Nullable Set<String> value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setNonNullStringSet(@NonNull String key, @NonNull Set<String> value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setInt(@NonNull String key, int value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setLong(@NonNull String key, long value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setFloat(@NonNull String key, float value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void setBoolean(@NonNull String key, boolean value) {
        putOrRemoveVal(key, value);
    }

    @Override
    public void beginTransaction() {
        if (isInTransaction()) {
            throw new RuntimeException("Multiple transactions are not allowed.");
        }

        temporaryMapForTransaction = new ConcurrentHashMap<String, Object>();
    }

    @Override
    public synchronized boolean finishTransaction(boolean commit) {
        if (!isInTransaction()) {
            return false;
        }

        if (temporaryMapForTransaction == null) {
            return false;
        }

        map.putAll(temporaryMapForTransaction);
        temporaryMapForTransaction = null;
        return true;
    }

    @Override
    public synchronized void cancelTransaction() {
        if (!isInTransaction()) {
            return; // just finish
        }

        temporaryMapForTransaction = null;

        if (Multipreference.DEBUG) {
            if (isInTransaction()) {
                throw new AssertionError("Transactions should be finished if canceled.");
            }
        }
    }

    @Override
    public boolean isInTransaction() {
        return temporaryMapForTransaction != null;
    }

    @Override
    public void clear() {
        temporaryMapForTransaction = null;
        map.clear();
    }

    @Override
    public void destroySelf() {
        temporaryMapForTransaction = null;
        map.clear();
    }

    /*
         * ConcurrentHashMap doesn't allow putting null value
         */
    private void putOrRemoveVal(@NonNull String key, @Nullable Object value) {
        if (value != null) {
            getMapToBeUpdated().put(key, value);
        } else {
            getMapToBeUpdated().remove(key);
        }
    }

    @NonNull
    private Map<String, Object> getMapToBeUpdated() {
        return temporaryMapForTransaction != null ? temporaryMapForTransaction : map;
    }

}
