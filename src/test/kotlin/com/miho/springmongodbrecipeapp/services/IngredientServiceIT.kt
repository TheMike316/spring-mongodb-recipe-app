package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.converters.IngredientToIngredientCommand
import com.miho.springmongodbrecipeapp.converters.RecipeToRecipeCommand
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest
class IngredientServiceIT {

    private companion object {
        const val NEW_DESCRIPTION = "new ingredient description"
    }

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Autowired
    private lateinit var ingredientService: IngredientService

    @Autowired
    private lateinit var recipeService: RecipeService

    @Autowired
    private lateinit var ingredientToCommand: IngredientToIngredientCommand

    @Autowired
    private lateinit var recipeToCommand: RecipeToRecipeCommand

    @Autowired
    private lateinit var uomService: UnitOfMeasureService


    @Test
    fun testUpdateIngredient() {

//		given
        val recipe = recipeRepository.findAll().iterator().next()
        val ingredient = recipe.ingredients.iterator().next()
        val ingredientCommand = ingredientToCommand.convert(ingredient)
        val recipeCommandBeforeUpdate = recipeToCommand.convert(recipe)

//		when
        ingredientCommand?.description = NEW_DESCRIPTION
        val savedIngredientCommand = ingredientService.saveOrUpdateIngredient(ingredientCommand!!, recipe.id).block()!!
        val recipeCommandAfterUpdate = recipeService.findById(recipe.id)

        //		then
        assertNotNull(savedIngredientCommand)
        assertEquals(NEW_DESCRIPTION, savedIngredientCommand.description)
        assertEquals(ingredient.id, savedIngredientCommand.id)
        assertEquals(recipe.id, recipeCommandAfterUpdate!!.id)
        assertEquals(ingredient.unitOfMeasure?.id, savedIngredientCommand.unitOfMeasure?.id)
        assertEquals(ingredient.amount, savedIngredientCommand.amount)

        assertEquals(recipeCommandBeforeUpdate?.id, recipeCommandAfterUpdate.id)
        assertEquals(recipeCommandBeforeUpdate?.description, recipeCommandAfterUpdate.description)
        assertEquals(recipeCommandBeforeUpdate?.categories, recipeCommandAfterUpdate.categories)
        assertEquals(recipeCommandBeforeUpdate?.difficulty, recipeCommandAfterUpdate.difficulty)
        assertEquals(recipeCommandBeforeUpdate?.ingredients?.size, recipeCommandAfterUpdate.ingredients.size)
        assert(recipeCommandAfterUpdate.ingredients.map { it.id }.indexOf(savedIngredientCommand.id) != -1)


    }


    @Test
    fun testNewIngredient() {

//		given
        val uom = uomService.listAllUoms().blockFirst()
        val recipe = recipeService.getAllRecipes().iterator().next()
        val newIngredientCommand = IngredientCommand(description = "new ingredient", unitOfMeasure = uom, amount = BigDecimal.ONE)

//		when
        val savedIngredientCommand = ingredientService.saveOrUpdateIngredient(newIngredientCommand, recipe.id).block()!!
        val recipeCommand = recipeService.findById(recipe.id)


//		then
        assertNotNull(savedIngredientCommand)
        assertNotNull(savedIngredientCommand.id)
        assertEquals(recipeCommand!!.id, recipe.id)
        assertEquals(savedIngredientCommand.description, newIngredientCommand.description)
        assertEquals(savedIngredientCommand.unitOfMeasure, newIngredientCommand.unitOfMeasure)
        assertEquals(savedIngredientCommand.amount, newIngredientCommand.amount)
        assert(recipeCommand.ingredients.indexOf(savedIngredientCommand) != -1)
    }


    @Test
    fun testDeleteIngredientHappyPath() {

//		given
        val recipe = recipeService.getAllRecipes().iterator().next()
        val ingredient = recipe.ingredients.iterator().next()
        val recipeId = recipe.id
        val ingredientId = ingredient.id

//		when
        ingredientService.deleteById(recipeId, ingredientId)

        var deletedIngredient: IngredientCommand? = null

        try {
            deletedIngredient = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block()
        } catch (e: RuntimeException) {
//			do nothing
        }

//		then
        assertEquals(null, deletedIngredient)
    }


    @Test(expected = RuntimeException::class)
    fun testDeleteIngredientSadPath() {

//		given
        val recipe = recipeService.getAllRecipes().iterator().next()
        val ingredient = recipe.ingredients.iterator().next()
        val recipeId = recipe.id + 10000
        val ingredientId = ingredient.id

//		when
        ingredientService.deleteById(recipeId, ingredientId)

//		then an exception is thrown
    }
}