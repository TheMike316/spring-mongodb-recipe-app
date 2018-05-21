package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UnitOfMeasureCommandToUnitOfMeasureTest {


    private val UNIT = "description"
    private val ID = "1"

    private var converter: UnitOfMeasureCommandToUnitOfMeasure? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = UnitOfMeasureCommandToUnitOfMeasure()
    }

    @Test
    @Throws(Exception::class)
    fun testNullParamter() {
        assertNull(converter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter!!.convert(UnitOfMeasureCommand("", "")))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given
        val command = UnitOfMeasureCommand(ID, UNIT)
//when
        val uom = converter!!.convert(command)
//then
        assertNotNull(uom)
        assertEquals(ID, uom!!.id)
        assertEquals(UNIT, uom.unit)
    }


}