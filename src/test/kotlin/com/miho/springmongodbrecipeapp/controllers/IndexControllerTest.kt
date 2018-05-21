package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.domain.Recipe
import com.miho.springmongodbrecipeapp.services.RecipeService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.ui.Model


class IndexControllerTest {

    private lateinit var controller: IndexController

    @Mock
    private var model: Model? = null

    @Mock
    private var service: RecipeService? = null

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        controller = IndexController(service!!)

    }

    @Test
    fun testMockMVC() {

        val mockMVC = MockMvcBuilders.standaloneSetup(controller).build()

        mockMVC.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("index"))

    }

    @Test
    fun getIndexPage() {

        assertEquals("index", controller.getIndexPage(model!!))

        verify(model, times(1))?.addAttribute(eq("recipes"), anySet<Recipe>())

        verify(service, times(1))?.getAllRecipes()


    }
}