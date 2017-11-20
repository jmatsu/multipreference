package com.github.jmatsu.multipreference

import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.properties.Delegates

@RunWith(RobolectricTestRunner::class)
class InMemoryDataStoreTest : DataStoreTestBase() {
    override var dataStore: DataStore by Delegates.notNull()

    @Before
    fun before() {
        dataStore = InMemoryDataStore()
    }
}