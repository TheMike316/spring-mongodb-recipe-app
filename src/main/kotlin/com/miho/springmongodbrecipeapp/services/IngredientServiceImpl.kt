package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.converters.IngredientCommandToIngredient
import com.miho.springmongodbrecipeapp.converters.IngredientToIngredientCommand
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IngredientServiceImpl(private val recipeRepository: RecipeRepository,
                            private val ingredientToCommand: IngredientToIngredientCommand,
                            private val commandToIngredient: IngredientCommandToIngredient) : IngredientService {

    override fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): IngredientCommand? {
        val recipeOpt = recipeRepository.findById(recipeId)

        if (!recipeOpt.isPresent)
            throw NotFoundException("Recipe not found!")

        try {
            return recipeOpt.get().ingredients.first { ingredientId == it.id }.let(ingredientToCommand::convert)
        } catch (e: NoSuchElementException) {
            throw NotFoundException("Ingredient not found!", e)
        }

    }

    override fun saveOrUpdateIngredient(ingredientCommand: IngredientCommand, recipeId: String): IngredientCommand {

        if (ingredientCommand.id == "")
            return saveNewIngredient(ingredientCommand, recipeId)

        return updateIngredient(ingredientCommand, recipeId)
    }

    private fun saveNewIngredient(ingredientCommand: IngredientCommand, recipeId: String): IngredientCommand {

        val ingredient = commandToIngredient.convert(ingredientCommand) ?: throw RuntimeException("Internal Error")

        val recipeOptional = recipeRepository.findById(recipeId)

        if (!recipeOptional.isPresent)
            throw NotFoundException("Recipe not found")

        recipeOptional.get().addIngredient(ingredient)

        val savedRecipe = recipeRepository.save(recipeOptional.get())

        val savedIngredient = savedRecipe.ingredients.first { ingredient.id == it.id }

        return ingredientToCommand.convert(savedIngredient)
                ?: throw RuntimeException("Internal Error")
    }

    private fun updateIngredient(ingredientCommand: IngredientCommand, recipeId: String): IngredientCommand {


        val recipe = recipeRepository.findById(recipeId)
                .orElseThrow { NotFoundException("recipe not found") }

        if (!recipe.ingredients.removeIf { ingredientCommand.id == it.id })
            throw NotFoundException("Ingredient not found in recipe")

        commandToIngredient.convert(ingredientCommand)?.apply { recipe.addIngredient(this) }
                ?: throw RuntimeException("Internal Error")

        return ingredientCommand
    }

    @Transactional
    override fun deleteById(recipeId: String, ingredientId: String) {

        val recipeOpt = recipeRepository.findById(recipeId)

        if (recipeOpt.isPresent && recipeOpt.get().ingredients.firstOrNull { it.id == ingredientId } != null) {

            recipeOpt.get().ingredients.removeIf { it.id == ingredientId }

            recipeRepository.save(recipeOpt.get())

        } else
            throw RuntimeException("Internal Error")

    }
}