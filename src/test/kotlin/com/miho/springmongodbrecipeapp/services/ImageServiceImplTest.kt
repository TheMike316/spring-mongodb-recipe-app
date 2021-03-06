package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.domain.Recipe
import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.mock.web.MockMultipartFile
import reactor.core.publisher.Mono
import org.mockito.Mockito.`when` as mockitoWhen

class ImageServiceImplTest {

    private lateinit var imageService: ImageService

    @Mock
    private lateinit var recipeRepository: RecipeReactiveRepository

    @Before
    fun startUp() {

        MockitoAnnotations.initMocks(this)

        imageService = ImageServiceImpl(recipeRepository)
    }


    @Test
    fun testSaveImageFile() {

//		given
        val recipeId = "1"
        val file = MockMultipartFile("imagefile", "test.txt", "text/plain", "Mike kicks ass".byteInputStream())
        val recipe = Recipe(id = recipeId)

        mockitoWhen(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe))
        mockitoWhen(recipeRepository.save(any<Recipe>())).thenReturn(Mono.just(recipe))
        val argumentCaptor = ArgumentCaptor.forClass(Recipe::class.java)

//		when
        imageService.saveImageFile(recipeId, file).block()

//		then
        verify(recipeRepository, times(1)).findById(anyString())
        verify(recipeRepository, times(1)).save(argumentCaptor.capture())
        val savedRecipe = argumentCaptor.value
        assertEquals(file.bytes.size, savedRecipe.image.size)


    }

}