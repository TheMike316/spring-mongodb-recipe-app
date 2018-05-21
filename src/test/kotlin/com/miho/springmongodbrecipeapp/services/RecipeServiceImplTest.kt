package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.converters.*
import com.miho.springmongodbrecipeapp.domain.Recipe
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*
import org.mockito.Mockito.`when` as mockitoWhen

class RecipeServiceImplTest {

    private var service: RecipeServiceImpl? = null


    @Mock
    private var repository: RecipeRepository? = null

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        val notesToCommand = NotesToNotesCommand()

        val unitToCommand = UnitOfMeasureToUnitOfMeasureCommand()

        val ingredientToCommand = IngredientToIngredientCommand(unitToCommand)

        val categoryToCommand = CategoryToCategoryCommand()

        val recipeToCommand = RecipeToRecipeCommand(notesToCommand, ingredientToCommand, categoryToCommand)

        val commandToNotes = NotesCommandToNotes()

        val commandToUnit = UnitOfMeasureCommandToUnitOfMeasure()

        val commandToIngredient = IngredientCommandToIngredient(commandToUnit)

        val commandToCategory = CategoryCommandToCategory()

        val commandToRecipe = RecipeCommandToRecipe(commandToNotes, commandToIngredient, commandToCategory)

        service = RecipeServiceImpl(repository!!, recipeToCommand, commandToRecipe)

    }

    @Test
    fun getRecipes() {

        val recipe = Recipe(id = "1", description = "")

        val mockRecipes = setOf(recipe)

        mockitoWhen(repository?.findAll()).thenReturn(mockRecipes)

        val recipes = service?.getAllRecipes()

        assertEquals(recipes?.size, 1)

        verify(repository, times(1))?.findAll()

    }

    @Test
    fun getRecipeById() {
        val recipe = Recipe(id = "1")

        mockitoWhen(repository?.findById(anyString())).thenReturn(Optional.of(recipe))

        val recipeReturned = service?.findById("1")

        assertNotNull("Null recipe returned", recipeReturned)

        verify(repository, times(1))?.findById(anyString())
        verify(repository, never())?.findAll()

    }

    @Test
    fun deleteById() {

        val idToDelete = "2"

        service?.deleteById(idToDelete)

        verify(repository, times(1))?.deleteById(anyString())

    }

    @Test(expected = NotFoundException::class)
    fun testFindByIdNotFound() {
//		given
        val recipeOptional: Optional<Recipe> = Optional.empty()

        mockitoWhen(repository?.findById(anyString())).thenReturn(recipeOptional)

//		when
        service?.findById("4")

//		should go boom
    }

}