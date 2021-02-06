package com.github.jmatsu.multipreference

import android.content.Context
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import org.mockito.Mockito.mock
import kotlin.test.assertSame

@RunWith(BlockJUnit4ClassRunner::class)
class MultipreferenceTest {

    @Test
    fun inMemory_should_returns_InMemoryDataStore() {
        assertSame(InMemoryDataStore::class.java, Multipreference.inMemory().javaClass)
    }

    @Test
    fun sharedPreferences_should_returns_SharedPreferencesDataStore() {
        assertSame(SharedPreferencesDataStore::class.java, Multipreference.sharedPreferences(mock(Context::class.java), "dummy").javaClass)
    }
}