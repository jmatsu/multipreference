package com.github.jmatsu.multipreference

import org.junit.Test
import java.util.*
import kotlin.test.*

abstract class DataStoreTestBase {
    abstract var dataStore: DataStore

    @Test
    fun getBoolean_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertFalse { dataStore.getBoolean("key", false) }
        assertTrue { dataStore.getBoolean("key", true) }
    }

    @Test
    fun getFloat_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertEquals(1f, dataStore.getFloat("key", 1f))
        assertEquals(-1f, dataStore.getFloat("key", -1f))
    }

    @Test
    fun getLong_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertEquals(1L, dataStore.getLong("key", 1L))
        assertEquals(-1L, dataStore.getLong("key", -1L))
    }

    @Test
    fun getInt_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertEquals(1, dataStore.getInt("key", 1))
        assertEquals(-1, dataStore.getInt("key", -1))
    }

    @Test
    fun getNullableString_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertNull(dataStore.getNullableString("key", null))
        assertEquals("value", dataStore.getNullableString("key", "value"))
    }

    @Test
    fun getNonNullString_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertEquals("value", dataStore.getNonNullString("key", "value"))
        assertEquals("value2", dataStore.getNonNullString("key", "value2"))
    }

    @Test
    fun getNullableStringSet_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertNull(dataStore.getNullableStringSet("key", null))
        assertEquals(Collections.singleton("value"), dataStore.getNullableStringSet("key", Collections.singleton("value")))
    }

    @Test
    fun getNonNullStringSet_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        assertEquals(Collections.singleton("value"), dataStore.getNonNullStringSet("key", Collections.singleton("value")))
    }

    @Test
    fun setAndThenGet_Boolean_should_return_a_recent_value() {
        dataStore.setBoolean("key", true)
        assertTrue { dataStore.getBoolean("key", false) }
    }

    @Test
    fun setAndThenGet_Float_should_return_a_recent_value() {
        dataStore.setFloat("key", 1f)
        assertEquals(1f, dataStore.getFloat("key", -1f))
    }

    @Test
    fun setAndThenGet_Long_should_return_a_recent_value() {
        dataStore.setLong("key", 1L)
        assertEquals(1L, dataStore.getLong("key", -1L))
    }

    @Test
    fun setAndThenGet_Int_should_return_a_recent_value() {
        dataStore.setInt("key", 1)
        assertEquals(1, dataStore.getInt("key", -1))
    }

    @Test
    fun setAndThenGet_NullableString_should_not_do_cache_null() {
        dataStore.setNullableString("key", null)
        assertEquals("value", dataStore.getNullableString("key", "value"))
    }

    @Test
    fun setAndThenGet_NullableString_should_return_a_recent_value_for_non_null() {
        dataStore.setNullableString("key", "value")
        assertEquals("value", dataStore.getNullableString("key", null))
    }

    @Test
    fun setAndThenGet_NonNullString_should_return_a_recent_value() {
        dataStore.setNonNullString("key", "value")
        assertEquals("value", dataStore.getNonNullString("key", "value2"))
    }

    @Test
    fun setAndThenGet_NullableStringSet_should_not_do_cache_null() {
        dataStore.setNullableStringSet("key", null)
        assertEquals(Collections.singleton("value"), dataStore.getNullableStringSet("key", Collections.singleton("value")))
    }

    @Test
    fun setAndThenGet_NullableStringSet_should_return_a_recent_value_for_non_null() {
        dataStore.setNullableStringSet("key", Collections.singleton("value"))
        assertEquals(Collections.singleton("value"), dataStore.getNullableStringSet("key", Collections.singleton("value2")))
    }

    @Test
    fun setAndThenGet_NonNullStringSet_should_return_a_recent_value() {
        dataStore.setNonNullStringSet("key", Collections.singleton("value"))
        assertEquals(Collections.singleton("value"), dataStore.getNonNullStringSet("key", Collections.singleton("value2")))
    }

    @Test
    fun clear_should_remove_all_data() {
        dataStore.setNullableStringSet("key1", Collections.singleton("value"))
        dataStore.setNullableString("key2", "value")

        assertNotNull(dataStore.getNullableStringSet("key1", null))
        assertNotNull(dataStore.getNullableString("key2", null))

        dataStore.clear()

        assertNull(dataStore.getNullableStringSet("key1", null))
        assertNull(dataStore.getNullableString("key2", null))
    }
}