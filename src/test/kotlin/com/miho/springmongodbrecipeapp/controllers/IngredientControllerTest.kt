package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.services.IngredientService
import com.miho.springmongodbrecipeapp.services.RecipeService
import com.miho.springmongodbrecipeapp.services.UnitOfMeasureService
import com.miho.springmongodbrecipeapp.testutils.any
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal
import org.mockito.Mockito.`when` as mockitoWhen


class IngredientControllerTest {

    private lateinit var controller: IngredientController

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var recipeService: RecipeService

    @Mock
    private lateinit var ingredientService: IngredientService

    @Mock
    private lateinit var uomService: UnitOfMeasureService

    @Before
    fun startUp() {
        MockitoAnnotations.initMocks(this)

        controller = IngredientController(recipeService, ingredientService, uomService)

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun testListIngredients() {
//		given
        val recipeCommand = RecipeCommand()
        mockitoWhen(recipeService.findById(anyString())).thenReturn(recipeCommand)

//		when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"))

        verify(recipeService, times(1)).findById(anyString())

    }

    @Test
    fun testShowIngredient() {
//		given
        val ingredientCommand = IngredientCommand(id = "2")

//		when
        mockitoWhen(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand)

//		then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"))
    }

    @Test
    fun testGetUpdateForm() {
//		given
        val ingredientCommand = IngredientCommand(id = "3")
        val uoms = setOf(UnitOfMeasureCommand(id = "1"), UnitOfMeasureCommand(id = "2"), UnitOfMeasureCommand(id = "3"))

//		when
        mockitoWhen(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand)
        mockitoWhen(uomService.listAllUoms()).thenReturn(uoms)

//		then
        mockMvc.perform(get("/recipe/2/ingredient/3/update"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))


    }

    @Test
    fun testPostUpdateIngredient() {

//		given
        val uom = UnitOfMeasureCommand(id = "2", unit = "Grams")
        val ingredientCommand = IngredientCommand(id = "4", description = "molten cheese", unitOfMeasure = uom, amount = BigDecimal(100))
        val recipeId = "1"

//		when
        mockitoWhen(ingredientService.saveOrUpdateIngredient(any(), any())).thenReturn(ingredientCommand)

//		then
        mockMvc.perform(post("/recipe/$recipeId/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ingredientCommand.id)
                .param("description", ingredientCommand.description)
                .param("amount", ingredientCommand.amount.toString())
                .param("unitOfMeasure.id", uom.id))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/$recipeId/ingredient/${ingredientCommand.id}/show"))

    }

    @Test
    fun testNewIngredientForm() {

//		given
        val recipeCommand = RecipeCommand(id = "1")

//		when
        mockitoWhen(recipeService.findById(anyString())).thenReturn(recipeCommand)
        mockitoWhen(uomService.listAllUoms()).thenReturn(emptySet())

//		then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))

    }

    @Test
    fun testDeletAction() {

        mockMvc.perform(get("/recipe/1/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/1/ingredients"))

        verify(ingredientService, times(1))?.deleteById(anyString(), anyString())
    }
}