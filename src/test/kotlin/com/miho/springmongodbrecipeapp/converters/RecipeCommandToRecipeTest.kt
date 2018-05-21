package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.*
import com.miho.springmongodbrecipeapp.domain.Difficulty
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class RecipeCommandToRecipeTest {


    private val RECIPE_ID = "1"
    private val COOK_TIME = 5
    private val PREP_TIME = 7
    private val DESCRIPTION = "My Recipe"
    private val DIRECTIONS = "Directions"
    private val DIFFICULTY = Difficulty.EASY
    private val SERVINGS = 3
    private val SOURCE = "Source"
    private val URL = "Some URL"
    private val CAT_ID_1 = "1"
    private val CAT_ID2 = "2"
    private val INGRED_ID_1 = "3"
    private val INGRED_ID_2 = "4"
    private val NOTES_ID = "9"

    private var converter: RecipeCommandToRecipe? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        converter = RecipeCommandToRecipe(NotesCommandToNotes(),
                IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure()), CategoryCommandToCategory()
        )
    }

    @Test
    @Throws(Exception::class)
    fun testNullObject() {
        assertNull(converter!!.convert(null))
    }

    @Test
    @Throws(Exception::class)
    fun testEmptyObject() {
        assertNotNull(converter!!.convert(RecipeCommand("", 0, 0, 0, "", "", "", NotesCommand("1", ""), emptySet<IngredientCommand>().toMutableSet(),
                Difficulty.EASY, emptySet<CategoryCommand>().toMutableSet())))
    }

    @Test
    @Throws(Exception::class)
    fun convert() {
//given
        val notes = NotesCommand(NOTES_ID, "")

        val category = CategoryCommand(CAT_ID_1, "")
        val category2 = CategoryCommand(CAT_ID2, "")

        val categories = mutableSetOf(category, category2)

        val uom = UnitOfMeasureCommand("1", "")

        val ingredient = IngredientCommand(id = INGRED_ID_1, description = "", amount = BigDecimal.ONE, unitOfMeasure = uom)
        val ingredient2 = IngredientCommand(id = INGRED_ID_2, description = "", amount = BigDecimal.ONE, unitOfMeasure = uom)

        val ingredients = mutableSetOf(ingredient, ingredient2)

        val recipeCommand = RecipeCommand(description = DESCRIPTION, prepTime = PREP_TIME, cookTime = COOK_TIME, servings = SERVINGS, source = SOURCE, url = URL,
                directions = DIRECTIONS, notes = notes, ingredients = ingredients, difficulty = DIFFICULTY, categories = categories, id = RECIPE_ID)


//when
        val recipe = converter!!.convert(recipeCommand)
        assertNotNull(recipe)
        assertEquals(RECIPE_ID, recipe!!.id)
        assertEquals(COOK_TIME, recipe.cookTime)
        assertEquals(PREP_TIME, recipe.prepTime)
        assertEquals(DESCRIPTION, recipe.description)
        assertEquals(DIFFICULTY, recipe.difficulty)
        assertEquals(DIRECTIONS, recipe.directions)
        assertEquals(SERVINGS, recipe.servings)
        assertEquals(SOURCE, recipe.source)
        assertEquals(URL, recipe.url)
        assertEquals(NOTES_ID, recipe.notes!!.id)
        assertEquals(2, recipe.categories.size)
        assertEquals(2, recipe.ingredients.size)
    }


}