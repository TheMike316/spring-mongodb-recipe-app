package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.domain.Ingredient
import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

/**
 * Created by jt on 6/21/17.
 */
class IngredientToIngredientCommandTest {


    private val AMOUNT = BigDecimal("1")
    private val DESCRIPTION = "Cheeseburger"
    private val UOM_ID = "2"
    private val ID_VALUE = "1"

    private var converter: IngredientToIngredientCommand? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand())
    }

    @Test
    @Throws(Exception::class)
    fun testNullConvert() {
        assertNull(converter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter!!.convert(Ingredient()))
    }

    @Test
    @Throws(Exception::class)
    fun testConvertNullUOM() {
//given
        val ingredient = Ingredient(id = ID_VALUE, amount = AMOUNT, description = DESCRIPTION)
//when
        val ingredientCommand = converter!!.convert(ingredient)
//then
        assertNull(ingredientCommand!!.unitOfMeasure)
        assertEquals(ID_VALUE, ingredientCommand.id)
        assertEquals(AMOUNT, ingredientCommand.amount)
        assertEquals(DESCRIPTION, ingredientCommand.description)
    }

    @Test
    @Throws(Exception::class)
    fun testConvertWithUom() {
//given		
        val uom = UnitOfMeasure("", UOM_ID)
        val ingredient = Ingredient(id = ID_VALUE, amount = AMOUNT, description = DESCRIPTION, unitOfMeasure = uom)
//when
        val ingredientCommand = converter!!.convert(ingredient)
//then
        assertEquals(ID_VALUE, ingredientCommand!!.id)
        assertNotNull(ingredientCommand.unitOfMeasure)
        assertEquals(UOM_ID, ingredientCommand.unitOfMeasure!!.id)
        assertEquals(AMOUNT, ingredientCommand.amount)
        assertEquals(DESCRIPTION, ingredientCommand.description)
    }


}