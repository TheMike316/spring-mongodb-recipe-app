package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.domain.Category
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by jt on 6/21/17.
 */
class CategoryToCategoryCommandTest {


    private val ID_VALUE = "1"
    private val DESCRIPTION = "descript"

    private var convter: CategoryToCategoryCommand? = null
    @Before
    @Throws(Exception::class)
    fun setUp() {
        convter = CategoryToCategoryCommand()
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(convter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(convter!!.convert(Category()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given
        val category = Category(description = DESCRIPTION, id = ID_VALUE)
//when
        val categoryCommand = convter!!.convert(category)
//then
        assertEquals(ID_VALUE, categoryCommand!!.id)
        assertEquals(DESCRIPTION, categoryCommand.description)
    }


}