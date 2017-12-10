package com.github.jmatsu.multipreference;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public interface DataStore {

    /**
     * Returns all pairs of key and value which are stored.
     *
     * @return a map of all keys and values. This map is not modifiable.
     * @see SharedPreferences#getAll()
     */
    @NonNull
    Map<String, ?> getAll();

    /**
     * Returns if this data store has a value which is associated to a given key
     *
     * @param key a name of this data store to obtain
     * @return whether or not a key has a non-null value.
     * @see SharedPreferences#contains(String)
     */
    boolean contains(@NonNull String key);

    /**
     * Obtain a nullable {@link String} value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of {@link String} except null
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    @Nullable
    String getNullableString(@NonNull String key, @Nullable String defaultValue);

    /**
     * Obtain a non-null {@link String} value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of {@link String} except null
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    @NonNull
    String getNonNullString(@NonNull String key, @NonNull String defaultValue);

    /**
     * Obtain a nullable {@link Set} of {@link String} value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of Set&lt;String&gt; except null
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    @Nullable
    Set<String> getNullableStringSet(@NonNull String key, @Nullable Set<String> defaultValue);

    /**
     * Obtain a nonnull {@link Set} of {@link String} value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of Set&lt;String&gt; except null
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    @NonNull
    Set<String> getNonNullStringSet(@NonNull String key, @NonNull Set<String> defaultValue);

    /**
     * Obtain an int value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of int
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    int getInt(@NonNull String key, int defaultValue);


    /**
     * Obtain a long value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of long
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    long getLong(@NonNull String key, long defaultValue);


    /**
     * Obtain a float value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of float
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    float getFloat(@NonNull String key, float defaultValue);


    /**
     * Obtain a boolean String value from this data store.
     * This may throw {@link ClassCastException} if an associated value is not an instance of boolean
     *
     * @param key          a name of this data store to obtain
     * @param defaultValue to be returned if a key doesn't have a non-null value.
     * @return a value which is associated with a key, or defaultValue.
     */
    boolean getBoolean(@NonNull String key, boolean defaultValue);

    /**
     * Set a nullable String value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setNullableString(@NonNull String key, @Nullable String value);

    /**
     * Set a nonnull String value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setNonNullString(@NonNull String key, @NonNull String value);

    /**
     * Set a nullable Set of String value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setNullableStringSet(@NonNull String key, @Nullable Set<String> value);

    /**
     * Set a nullable Set of String value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setNonNullStringSet(@NonNull String key, @NonNull Set<String> value);

    /**
     * Set an int value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setInt(@NonNull String key, int value);

    /**
     * Set a long value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setLong(@NonNull String key, long value);

    /**
     * Set a float value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setFloat(@NonNull String key, float value);

    /**
     * Set a boolean value to this data store immediately.
     *
     * @param key   a name of this data store to obtain
     * @param value to be set
     */
    void setBoolean(@NonNull String key, boolean value);

    /**
     * Begin a transaction.
     *
     * @see DataStore#finishTransaction(boolean)
     * @see DataStore#cancelTransaction()
     */
    void beginTransaction();

    /**
     * Finish the current transaction successfully
     *
     * @param commit whether do use SharedPreferences.Editor#commit() or not
     * @return committed or applied successfully.
     */
    boolean finishTransaction(boolean commit);

    /**
     * Drop modifications which are applied in the current transaction.
     */
    void cancelTransaction();

    /**
     * Whether or not this data store is in a transaction
     *
     * @return If this data store is in a transaction, this returns true. Otherwise, false.
     */
    boolean isInTransaction();

    /**
     * clear all. This will ignore transaction.
     */
    void clear();

    /**
     * destroy a file. Destroyed datastore cannot be used anymore.
     */
    void destroySelf();
}
