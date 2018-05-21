package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.converters.RecipeCommandToRecipe
import com.miho.springmongodbrecipeapp.converters.RecipeToRecipeCommand
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional
class RecipeServiceImpl(private val recipeRepository: RecipeRepository,
                        private val recipeToCommand: RecipeToRecipeCommand,
                        private val commandToRecipe: RecipeCommandToRecipe) : RecipeService {

    override fun findById(id: String): RecipeCommand? {

        val recipe = recipeRepository.findById(id)

        if (recipe.isPresent)
            return recipeToCommand.convert(recipe.get())

        throw NotFoundException("Recipe not found!")


    }

    override fun getAllRecipes() = recipeRepository.findAll().asSequence().map(recipeToCommand::convert).filter { it != null }.map { it as RecipeCommand }.toSet()

    override fun saveAll(recipes: Iterable<RecipeCommand>): Iterable<RecipeCommand> = recipes.asSequence().map(::saveRecipe).toList()

    override fun saveRecipe(recipeCommand: RecipeCommand?): RecipeCommand {

        if (recipeCommand == null)
            throw RuntimeException("internal Error")

        val recipe = commandToRecipe.convert(recipeCommand) ?: throw IllegalArgumentException()

        val savedRecipe = recipeRepository.save(recipe)

        return recipeToCommand.convert(savedRecipe) ?: throw RuntimeException("internal Error")

    }

    override fun deleteById(id: String) = recipeRepository.deleteById(id)
}