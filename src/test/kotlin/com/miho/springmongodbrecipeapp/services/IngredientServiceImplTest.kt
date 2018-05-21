package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.converters.IngredientCommandToIngredient
import com.miho.springmongodbrecipeapp.converters.IngredientToIngredientCommand
import com.miho.springmongodbrecipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure
import com.miho.springmongodbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.domain.Ingredient
import com.miho.springmongodbrecipeapp.domain.Recipe
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*
import org.mockito.Mockito.`when` as mockitoWhen

class IngredientServiceImplTest {

    private lateinit var ingredientService: IngredientService


    @Mock
    private lateinit var recipeRepository: RecipeRepository


    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        val unitToCommand = UnitOfMeasureToUnitOfMeasureCommand()

        val ingredientToCommand = IngredientToIngredientCommand(unitToCommand)

        val commandToUnit = UnitOfMeasureCommandToUnitOfMeasure()

        val commandToIngredient = IngredientCommandToIngredient(commandToUnit)

        ingredientService = IngredientServiceImpl(recipeRepository, ingredientToCommand, commandToIngredient)


    }

    @Test
    fun testFindByRecipeIdAndIngredientId() {
//		given
        val recipe = Recipe(id = "1")

        val ingredient1 = Ingredient(id = "1")
        val ingredient2 = Ingredient(id = "2")
        val ingredient3 = Ingredient(id = "3")

        recipe.addIngredients(listOf(ingredient1, ingredient2, ingredient3))


        mockitoWhen(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe))

//      when		
        val ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2")

//		then
        assertEquals("2", ingredientCommand?.id)
        verify(recipeRepository, times(1)).findById(anyString())
    }

    @Test
    fun testDeleteByIdHappyPath() {

//		given
        val ingredient = Ingredient(id = "2")
        val recipe = Recipe(id = "1", ingredients = mutableSetOf(ingredient))

//	 	when
        mockitoWhen(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe))
        ingredientService.deleteById(recipe.id, ingredient.id)

//		then
        verify(recipeRepository, times(1)).findById(anyString())
        verify(recipeRepository, times(1)).save(any())
    }

    @Test(expected = RuntimeException::class)
    fun testDeleteByIdSadPath() {
//		given
        val recipe = Recipe(id = "2")
        val ingredientId = "2"

//	 	when
        mockitoWhen(recipeRepository.findById(anyString())).thenReturn(Optional.of(recipe))
        ingredientService.deleteById(recipe.id, ingredientId)

//		then expected exception is thrown
    }

}