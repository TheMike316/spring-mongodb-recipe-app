package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.CategoryCommand
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CategoryCommandToCategoryTest {

    private val ID_VALUE = "1"
    private val DESCRIPTION = "description"

    private var conveter: CategoryCommandToCategory? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        conveter = CategoryCommandToCategory()
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(conveter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(conveter!!.convert(CategoryCommand("", "")))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given
        val categoryCommand = CategoryCommand(ID_VALUE, DESCRIPTION)
//when
        val category = conveter!!.convert(categoryCommand)
//then
        assertEquals(ID_VALUE, category!!.id)
        assertEquals(DESCRIPTION, category.description)
    }


}