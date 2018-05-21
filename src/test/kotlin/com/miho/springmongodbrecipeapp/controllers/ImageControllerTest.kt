package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.services.ImageService
import com.miho.springmongodbrecipeapp.services.RecipeService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.mockito.Mockito.`when` as mockitoWhen

class ImageControllerTest {

    @Mock
    private lateinit var imageService: ImageService

    @Mock
    private lateinit var recipeService: RecipeService

    private lateinit var controller: ImageController

    private lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        controller = ImageController(recipeService, imageService)

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    }

    @Test
    fun testFormGet() {
//		given
        val recipeCommand = RecipeCommand(id = "1")

        mockitoWhen(recipeService.findById(anyString())).thenReturn(recipeCommand)

//		when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk)
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"))

//		then
        verify(recipeService, times(1)).findById(anyString())
    }

    @Test
    fun testImagePost() {
//		given
        val file = MockMultipartFile("imagefile", "test.txt", "text/plain", "Mike kicks ass".byteInputStream())

//		when
        mockMvc.perform(multipart("/recipe/1/image/upload").file(file))
                .andExpect(status().is3xxRedirection)
                .andExpect(view().name("redirect:/recipe/1/show"))

//		then
        verify(imageService, times(1)).saveImageFile(anyString(), any())

    }

    @Test
    fun testRenderImageFromDb() {

//		given
        val s = "fake image text"

        val bytes: ByteArray = s.byteInputStream().use { it.readBytes() }

        val recipeCommand = RecipeCommand(id = "1", image = bytes)


        mockitoWhen(recipeService.findById(anyString())).thenReturn(recipeCommand)

//		when
        val response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk)
                .andReturn().response

        val responseBytes = response.contentAsByteArray

        assertEquals(bytes.size, responseBytes.size)

    }

}