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
import reactor.core.publisher.Flux


class IndexControllerTest {

    private lateinit var controller: IndexController

    @Mock
    private lateinit var model: Model

    @Mock
    private lateinit var service: RecipeService

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        controller = IndexController(service)

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

        org.mockito.Mockito.`when`(service.getAllRecipes()).thenReturn(Flux.empty())

        assertEquals("index", controller.getIndexPage(model))

        verify(model, times(1))?.addAttribute(eq("recipes"), any<Flux<Recipe>>())

        verify(service, times(1))?.getAllRecipes()


    }
}