package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class IngredientCommandToIngredientTest {


    private val AMOUNT = BigDecimal("1")
    private val DESCRIPTION = "Cheeseburger"
    private val ID_VALUE = "1"
    private val UOM_ID = "2"

    private var converter: IngredientCommandToIngredient? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure())
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(converter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter!!.convert(IngredientCommand(id = "", description = "", amount = BigDecimal.ZERO, unitOfMeasure = UnitOfMeasureCommand("", ""))))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given

        val unitOfMeasureCommand = UnitOfMeasureCommand(UOM_ID, "")
        val command = IngredientCommand(id = ID_VALUE, description = DESCRIPTION, amount = AMOUNT, unitOfMeasure = unitOfMeasureCommand)
//when
        val ingredient = converter!!.convert(command)
//then
        assertNotNull(ingredient)
        assertNotNull(ingredient!!.unitOfMeasure)
        assertEquals(ID_VALUE, ingredient.id)
        assertEquals(AMOUNT, ingredient.amount)
        assertEquals(DESCRIPTION, ingredient.description)
        assertEquals(UOM_ID, ingredient.unitOfMeasure!!.id)
    }

    @Test
    @Throws(Exception::class)
    fun convertWithNullUOM() {
//given

        val command = IngredientCommand(id = ID_VALUE, description = DESCRIPTION, amount = AMOUNT)
//when
        val ingredient = converter!!.convert(command)
//then
        assertNotNull(ingredient)
        assertNull(ingredient!!.unitOfMeasure)
        assertEquals(ID_VALUE, ingredient.id)
        assertEquals(AMOUNT, ingredient.amount)
        assertEquals(DESCRIPTION, ingredient.description)
    }


}