package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.converters.RecipeToRecipeCommand
import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


//@DataJpaTest The test would not be able to find an implementation of the RecipeService when using this annotation,
//because then only a lightened version of our Spring Context would be brought up
@RunWith(SpringRunner::class)
@SpringBootTest
class RecipeServiceIT {

    private val NEW_DESCRIPTION = "new description"

    @Autowired
    private lateinit var recipeService: RecipeService

    @Autowired
    private lateinit var recipeRepository: RecipeReactiveRepository

    @Autowired
    private lateinit var recipeToCommand: RecipeToRecipeCommand


    @Test
    fun testSaveOfDescription() {
//		given
        val testRecipe = recipeRepository.findAll().blockFirst()
        val testRecipeCommand = recipeToCommand.convert(testRecipe)

//		when
        testRecipeCommand?.description = NEW_DESCRIPTION
        val savedRecipeCommand = recipeService.saveRecipe(testRecipeCommand!!).block()

//		then
        assertNotNull(savedRecipeCommand)
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand!!.description)
        assertEquals(testRecipe!!.id, savedRecipeCommand.id)
        assertEquals(testRecipe.categories.size, savedRecipeCommand.categories?.size)
        assertEquals(testRecipe.ingredients.size, savedRecipeCommand.ingredients?.size)

    }
}