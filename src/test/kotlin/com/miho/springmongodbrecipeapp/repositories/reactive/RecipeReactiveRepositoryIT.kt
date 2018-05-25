package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.Difficulty
import com.miho.springmongodbrecipeapp.domain.Ingredient
import com.miho.springmongodbrecipeapp.domain.Recipe
import com.miho.springmongodbrecipeapp.testutils.TestDataHelper
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest
class RecipeReactiveRepositoryIT {

    @Autowired
    private lateinit var recipeReactiveRepository: RecipeReactiveRepository

    @Autowired
    private lateinit var categoryReactiveRepository: CategoryReactiveRepository

    @Autowired
    private lateinit var unitOfMeasureReactiveRepository: UnitOfMeasureReactiveRepository

    @Autowired
    private lateinit var testDataHelper: TestDataHelper

    @After
    fun tearDown() {
        testDataHelper.resetRecipeData()
    }

    @Test
    fun testFindAll() {
        val count = recipeReactiveRepository.findAll().count().block()

        assertEquals(2L, count)
    }

    @Test
    fun testFindById() {
        val recipe = recipeReactiveRepository.findById("1").block()
        assertTrue(recipe != null)

        assertEquals("How to Make Perfect Guacamole Recipe", recipe!!.description)
        assertEquals("Mexican", recipe.categories.iterator().next().description)
        assertEquals(Difficulty.EASY, recipe.difficulty)
    }

    @Test
    fun testInsert() {
        val category = categoryReactiveRepository.findByDescription("Austrian").block()!!

        val newRecipe = Recipe(description = "The most awesome recipe ever created in the history of ever",
                prepTime = 4,
                cookTime = 4,
                servings = 5,
                directions = "Cry a lot, order a pizza, cry some more and go to sleep",
                categories = mutableSetOf(category))

        val persistedRecipe = recipeReactiveRepository.insert(newRecipe).block()

        assertTrue(persistedRecipe != null)
        assertEquals(newRecipe.description, persistedRecipe!!.description)
        assertEquals(newRecipe.prepTime, persistedRecipe.prepTime)
        assertEquals(newRecipe.cookTime, persistedRecipe.cookTime)
        assertEquals(newRecipe.servings, persistedRecipe.servings)
        assertEquals(newRecipe.directions, persistedRecipe.directions)
        assertEquals(newRecipe.categories.iterator().next().description, persistedRecipe.categories.iterator().next().description)
    }

    @Test
    fun testUpdate() {
        val recipeCountBefore = recipeReactiveRepository.findAll().count().block()

        val recipe = recipeReactiveRepository.findAll().blockFirst()!!

        val ingredientCountBefore = recipe.ingredients.size

        val newDirections = "Don't tell me what to do!"
        val newDescription = "memento mori"
        val recipeId = recipe.id

        recipe.directions = newDirections
        recipe.description = newDescription
        recipe.difficulty = Difficulty.KIND_OF_HARD

        val ingredient = recipe.ingredients.iterator().next()
        val ingredientId = ingredient.id
        val newAmount = BigDecimal.TEN
        val newIngredientDescription = "Used vintage vermilion bricks"

        ingredient.amount = newAmount
        ingredient.description = newIngredientDescription

        val persistedRecipe = recipeReactiveRepository.save(recipe).block()
        assertTrue(persistedRecipe != null)

        assertEquals(recipeCountBefore, recipeReactiveRepository.findAll().count().block())
        assertEquals(recipeId, persistedRecipe!!.id)
        assertEquals(newDescription, persistedRecipe.description)
        assertEquals(Difficulty.KIND_OF_HARD, persistedRecipe.difficulty)
        assertEquals(newDirections, persistedRecipe.directions)

        assertEquals(ingredientCountBefore, persistedRecipe.ingredients.size)
        val persistedIngredient = persistedRecipe.ingredients.find { it.id == ingredientId }
        assertTrue(persistedIngredient != null)
        assertEquals(newAmount, persistedIngredient!!.amount)
        assertEquals(newIngredientDescription, persistedIngredient.description)
    }

    @Test
    fun testAddIngredient() {
        val recipeCountBefore = recipeReactiveRepository.findAll().count().block()

        val recipe = recipeReactiveRepository.findAll().blockFirst()!!
        val ingredientCountBefore = recipe.ingredients.size

        val uom = unitOfMeasureReactiveRepository.findAll().blockFirst()!!
        val newIngredient = Ingredient("Baumfisch", BigDecimal.ONE, uom)

        recipe.addIngredient(newIngredient)

        val persistedRecipe = recipeReactiveRepository.save(recipe).block()!!
        val persistedIngredient = persistedRecipe.ingredients.find { it.id == newIngredient.id }
        assertTrue(persistedIngredient != null)
        assertEquals(newIngredient.description, persistedIngredient!!.description)
        assertEquals(newIngredient.amount, persistedIngredient.amount)
        assertEquals(newIngredient.unitOfMeasure, persistedIngredient.unitOfMeasure)

        assertEquals(recipeCountBefore, recipeReactiveRepository.findAll().count().block())
        assertTrue(persistedRecipe.ingredients.size > ingredientCountBefore)
    }

    @Test
    fun testRemoveIngredient() {
        val recipe = recipeReactiveRepository.findAll().blockFirst()!!

        val ingredientCountBefore = recipe.ingredients.size
        val ingredietToBeDeleted = recipe.ingredients.iterator().next()

        assertTrue(recipe.ingredients.remove(ingredietToBeDeleted))

        val persistedRecipe = recipeReactiveRepository.save(recipe).block()!!
        assertFalse(persistedRecipe.ingredients.contains(ingredietToBeDeleted))
        assertTrue(ingredientCountBefore > persistedRecipe.ingredients.size)
    }
}