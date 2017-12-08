package com.github.jmatsu.multipreference

import org.amshove.kluent.*
import org.junit.Test
import java.util.*

abstract class DataStoreTestBase {
    abstract var dataStore: DataStore

    @Test
    fun getBoolean_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getBoolean("key", false).shouldBeFalse()
        dataStore.getBoolean("key", true).shouldBeTrue()
    }

    @Test
    fun getFloat_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getFloat("key", 1f) shouldEqualTo (1f)
        dataStore.getFloat("key", -1f) shouldEqualTo (-1f)
    }

    @Test
    fun getLong_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getLong("key", 1L) shouldEqualTo (1L)
        dataStore.getLong("key", -1L) shouldEqualTo (-1L)

    }

    @Test
    fun getInt_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getInt("key", 1) shouldEqualTo (1)
        dataStore.getInt("key", -1) shouldEqualTo (-1)
    }

    @Test
    fun getNullableString_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getNullableString("key", null).shouldBeNull()
        dataStore.getNullableString("key", "value")?.shouldEqualTo("value") ?: throw AssertionError("Expected non-null value, but null was returned")
    }

    @Test
    fun getNonNullString_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getNonNullString("key", "value") shouldEqualTo ("value")
        dataStore.getNonNullString("key", "value2") shouldEqualTo ("value2")
    }

    @Test
    fun getNullableStringSet_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getNullableStringSet("key", null).shouldBeNull()
        dataStore.getNullableStringSet("key", Collections.singleton("value")) shouldEqual (Collections.singleton("value"))
    }

    @Test
    fun getNonNullStringSet_should_return_a_default_value_if_a_key_does_not_associate_any_values() {
        dataStore.getNonNullStringSet("key", Collections.singleton("value")) shouldEqual (Collections.singleton("value"))
    }

    @Test
    fun setAndThenGet_Boolean_should_return_a_recent_value() {
        dataStore.setBoolean("key", true)
        dataStore.getBoolean("key", false).shouldBeTrue()
    }

    @Test
    fun setAndThenGet_Float_should_return_a_recent_value() {
        dataStore.setFloat("key", 1f)
        dataStore.getFloat("key", -1f) shouldEqualTo (1f)
    }

    @Test
    fun setAndThenGet_Long_should_return_a_recent_value() {
        dataStore.setLong("key", 1L)
        dataStore.getLong("key", -1L) shouldEqualTo (1L)
    }

    @Test
    fun setAndThenGet_Int_should_return_a_recent_value() {
        dataStore.setInt("key", 1)
        dataStore.getInt("key", -1) shouldEqualTo (1)
    }

    @Test
    fun setAndThenGet_NullableString_should_not_do_cache_null() {
        dataStore.setNullableString("key", null)
        dataStore.getNullableString("key", "value") shouldEqual ("value")
    }

    @Test
    fun setAndThenGet_NullableString_should_return_a_recent_value_for_non_null() {
        dataStore.setNullableString("key", "value")
        dataStore.getNullableString("key", null) shouldEqual ("value")
    }

    @Test
    fun setAndThenGet_NonNullString_should_return_a_recent_value() {
        dataStore.setNonNullString("key", "value")
        dataStore.getNonNullString("key", "value2") shouldEqualTo ("value")
    }

    @Test
    fun setAndThenGet_NullableStringSet_should_not_do_cache_null() {
        dataStore.setNullableStringSet("key", null)
        dataStore.getNullableStringSet("key", Collections.singleton("value")) shouldEqual (Collections.singleton("value"))
    }

    @Test
    fun setAndThenGet_NullableStringSet_should_return_a_recent_value_for_non_null() {
        dataStore.setNullableStringSet("key", Collections.singleton("value"))
        dataStore.getNullableStringSet("key", Collections.singleton("value2")) shouldEqual (Collections.singleton("value"))
    }

    @Test
    fun setAndThenGet_NonNullStringSet_should_return_a_recent_value() {
        dataStore.setNonNullStringSet("key", Collections.singleton("value"))
        dataStore.getNonNullStringSet("key", Collections.singleton("value2")) shouldEqual (Collections.singleton("value"))
    }

    @Test
    fun clear_should_remove_all_data() {
        dataStore.setNullableStringSet("key1", Collections.singleton("value"))
        dataStore.setNullableString("key2", "value")

        dataStore.getNullableStringSet("key1", null).shouldNotBeNull()
        dataStore.getNullableString("key2", null).shouldNotBeNull()

        dataStore.clear()

        dataStore.getNullableStringSet("key1", null).shouldBeNull()
        dataStore.getNullableString("key2", null).shouldBeNull()
    }
}