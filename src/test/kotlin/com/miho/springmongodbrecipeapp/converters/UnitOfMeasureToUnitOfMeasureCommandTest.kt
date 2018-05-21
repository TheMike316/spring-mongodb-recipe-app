package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by jt on 6/21/17.
 */
class UnitOfMeasureToUnitOfMeasureCommandTest {


    private val UNIT = "description"
    private val ID = "1"

    private var converter: UnitOfMeasureToUnitOfMeasureCommand? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = UnitOfMeasureToUnitOfMeasureCommand()
    }

    @Test
    @Throws(Exception::class)
    fun testNullObjectConvert() {
        assertNull(converter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObj() {
        assertNotNull(converter!!.convert(UnitOfMeasure()))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given
        val uom = UnitOfMeasure(UNIT, ID)
//when
        val uomc = converter!!.convert(uom)
//then
        assertEquals(ID, uomc!!.id)
        assertEquals(UNIT, uomc.unit)
    }

}