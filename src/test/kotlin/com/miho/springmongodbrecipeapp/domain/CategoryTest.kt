package com.miho.springmongodbrecipeapp.domain

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CategoryTest {

    private var category: Category? = null

    @Before
    fun setUp() {
        category = Category()
    }

    @Test
    fun getId() {

        val idValue = "4"

        category?.id = idValue

        assertEquals(idValue, category?.id)

    }

    @Test
    fun getDescription() {

    }

    @Test
    fun getRecipes() {

    }

}