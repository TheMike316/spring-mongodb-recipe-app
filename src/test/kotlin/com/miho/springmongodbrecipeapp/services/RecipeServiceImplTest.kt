package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.converters.*
import com.miho.springmongodbrecipeapp.domain.Recipe
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import com.miho.springmongodbrecipeapp.testutils.any
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.mockito.Mockito.`when` as mockitoWhen

class RecipeServiceImplTest {

    private lateinit var service: RecipeServiceImpl


    @Mock
    private lateinit var repository: RecipeReactiveRepository

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

        val mockRecipes = Flux.just(recipe)

        mockitoWhen(repository.findAll()).thenReturn(mockRecipes)

        val recipes = service.getAllRecipes().toIterable()

        assertEquals(recipes.count(), 1)

        verify(repository, times(1)).findAll()

    }

    @Test
    fun getRecipeById() {
        val recipe = Recipe(id = "1")

        mockitoWhen(repository.findById(anyString())).thenReturn(Mono.just(recipe))

        val recipeReturned = service.findById("1").block()

        assertNotNull("Null recipe returned", recipeReturned)

        verify(repository, times(1)).findById(anyString())
        verify(repository, never()).findAll()

    }

    @Test
    fun deleteById() {

        val idToDelete = "2"

        mockitoWhen(repository.deleteById(any<String>())).thenReturn(Mono.empty())

        service.deleteById(idToDelete).block()

        verify(repository, times(1)).deleteById(any<String>())

    }

    @Ignore //TODO error handling when no Recipe could be found
    @Test(expected = NotFoundException::class)
    fun testFindByIdNotFound() {
//		given
        val recipeMono = Mono.empty<Recipe>()

        mockitoWhen(repository.findById(anyString())).thenReturn(recipeMono)

//		when
        service.findById("4").block()

//		should go boom
    }

}