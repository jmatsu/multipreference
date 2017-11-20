package com.github.jmatsu.multipreference

import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.properties.Delegates

@RunWith(RobolectricTestRunner::class)
class SharedPreferencesDataStoreTest : DataStoreTestBase() {
    val preferenceName = "SharedPreferencesDataStoreTest"

    override var dataStore: DataStore by Delegates.notNull()

    @Before
    fun before() {
        dataStore = SharedPreferencesDataStore(RuntimeEnvironment.application, preferenceName)
    }

    @After
    fun after() {
        RuntimeEnvironment.application.clearSharedPreferences(preferenceName)
    }

    private fun Context.clearSharedPreferences(name: String) {
        getSharedPreferences(name, Context.MODE_PRIVATE).edit().clear().commit()
    }
}