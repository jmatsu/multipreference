package com.github.jmatsu.multipreference

import org.amshove.kluent.mock
import org.amshove.kluent.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class MultipreferenceTest {

    @Test
    fun inMemory_should_returns_InMemoryDataStore() {
        Multipreference.inMemory().javaClass.shouldBe(InMemoryDataStore::class.java)
    }

    @Test
    fun sharedPreferences_should_returns_SharedPreferencesDataStore() {
        Multipreference.sharedPreferences(mock(), "dummy").javaClass.shouldBe(SharedPreferencesDataStore::class.java)
    }
}