package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.commands.CategoryCommand
import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.commands.NotesCommand
import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.domain.Difficulty
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.services.RecipeService
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import reactor.core.publisher.Mono
import org.mockito.Mockito.`when` as mockitoWhen

class RecipeControllerTest {

    private lateinit var controller: RecipeController

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var service: RecipeService

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        controller = RecipeController(service)

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ControllerExceptionHandler()).build()

    }

    @Test
    fun testGetRecipe() {

        val recipe = Mono.just(RecipeCommand(id = "1", description = "", prepTime = 0, cookTime = 0, servings = 0,
                source = "", url = "", directions = "", notes = NotesCommand("1", ""), ingredients = emptySet<IngredientCommand>().toMutableSet(),
                difficulty = Difficulty.EASY, categories = emptySet<CategoryCommand>().toMutableSet()))



        mockitoWhen(service.findById(anyString())).thenReturn(recipe)

        mockMvc.perform(get("/recipe/1/show")).andExpect(status().isOk).andExpect(view().name("recipe/show"))


    }

    @Test
    fun testGetRecipeNotFound() {

        mockitoWhen(service.findById(anyString())).thenThrow(NotFoundException::class.java)

        mockMvc.perform(get("/recipe/4/show"))
                .andExpect(status().isNotFound)
                .andExpect(view().name("404error"))
    }

    @Test
    fun testGetNewRecipeForm() {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))

    }

    @Test
    fun testPostNewRecipeForm() {

        val id = "2"
        val command = Mono.just(RecipeCommand(id = id))

        mockitoWhen(service.saveRecipe(any<RecipeCommand>())).thenReturn(command)

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", id)
                .param("description", "assduff")
                .param("prepTime", "4")
                .param("cookTime", "30")
                .param("servings", "4")
                .param("directions", "asdf"))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/$id/show"))


    }

    @Test
    fun testPostNewRecipeFormFailValidation() {

        val id = "2"
        val command = Mono.just(RecipeCommand(id = id))

        mockitoWhen(service.saveRecipe(any<RecipeCommand>())).thenReturn(command)

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", id)
                .param("description", ""))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/recipeform"))


    }

    @Test
    fun testGetUpdateView() {

        val command = Mono.just(RecipeCommand(id = "2"))

        mockitoWhen(service.findById(anyString())).thenReturn(command)

        mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"))

    }

    @Test
    fun testDeletAction() {

        mockitoWhen(service.deleteById(com.miho.springmongodbrecipeapp.testutils.any())).thenReturn(Mono.empty())

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/"))

        verify(service, times(1)).deleteById(anyString())
    }
}

