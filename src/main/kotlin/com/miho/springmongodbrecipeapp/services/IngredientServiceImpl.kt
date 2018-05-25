package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.converters.IngredientCommandToIngredient
import com.miho.springmongodbrecipeapp.converters.IngredientToIngredientCommand
import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class IngredientServiceImpl(private val recipeRepository: RecipeReactiveRepository,
                            private val ingredientToCommand: IngredientToIngredientCommand,
                            private val commandToIngredient: IngredientCommandToIngredient) : IngredientService {

    override fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): Mono<IngredientCommand> {
        return recipeRepository.findById(recipeId)
                .map { it.ingredients.find { i -> ingredientId == i.id } }
                .map { ingredientToCommand.convert(it) ?: throw  RuntimeException("ingredient was not found") }

//        val recipeOpt = recipeRepository.findById(recipeId)
//
//        if (!recipeOpt.isPresent)
//            throw NotFoundException("Recipe not found!")
//
//        try {
//            return recipeOpt.get().ingredients.first { ingredientId == it.id }.let(ingredientToCommand::convert)
//        } catch (e: NoSuchElementException) {
//            throw NotFoundException("Ingredient not found!", e)
//        }

    }

    override fun saveOrUpdateIngredient(ingredientCommand: IngredientCommand, recipeId: String): Mono<IngredientCommand> {
        val ingredient = commandToIngredient.convert(ingredientCommand) ?: throw  IllegalArgumentException("ingredient is null")

        return recipeRepository.findById(recipeId)
                .doOnNext {
                    it.ingredients.removeIf { i -> ingredient.id == i.id }
                    it.addIngredient(ingredient)
                    recipeRepository.save(it)
                }
                .map { it.ingredients.find { i -> ingredient.id == i.id } }
                .map { ingredientToCommand.convert(it) ?: throw RuntimeException("Internal Error!") }

//        if (ingredientCommand.id == "")
//            return saveNewIngredient(ingredientCommand, recipeId)
//
//        return updateIngredient(ingredientCommand, recipeId)
    }

//    private fun saveNewIngredient(ingredientCommand: IngredientCommand, recipeId: String): Mono<IngredientCommand> {
//
//        val ingredient = commandToIngredient.convert(ingredientCommand) ?: throw  IllegalArgumentException("ingredient is null")
//
//        return recipeRepository.findById(recipeId)
//                .doOnNext {
//                    it.ingredients.removeIf { i -> ingredient.id == i.id }
//                    it.addIngredient(ingredient)
//                }
//                .doOnNext { recipeRepository.save(it) }
//                .map { it.ingredients.find { i -> i.id == ingredient.id } }
//                .map { ingredientToCommand.convert(it) ?: throw IllegalArgumentException("ingredient is null") }

//        val ingredient = commandToIngredient.convert(ingredientCommand) ?: throw RuntimeException("Internal Error")
//
//        val recipeOptional = recipeRepository.findById(recipeId)
//
//        if (!recipeOptional.isPresent)
//            throw NotFoundException("Recipe not found")
//
//        recipeOptional.get().addIngredient(ingredient)
//
//        val savedRecipe = recipeRepository.save(recipeOptional.get())
//
//        val savedIngredient = savedRecipe.ingredients.first { ingredient.id == it.id }
//
//        return ingredientToCommand.convert(savedIngredient)
//                ?: throw RuntimeException("Internal Error")
//}

//    private fun updateIngredient(ingredientCommand: IngredientCommand, recipeId: String): Mono<IngredientCommand> {
//
//
//        val recipe = recipeRepository.findById(recipeId)
//                .orElseThrow { NotFoundException("recipe not found") }
//
//        if (!recipe.ingredients.removeIf { ingredientCommand.id == it.id })
//            throw NotFoundException("Ingredient not found in recipe")
//
//        commandToIngredient.convert(ingredientCommand)?.apply { recipe.addIngredient(this) }
//                ?: throw RuntimeException("Internal Error")
//
//        return ingredientCommand
//    }

    @Transactional
    override fun deleteById(recipeId: String, ingredientId: String): Mono<Unit> {

        return recipeRepository.findById(recipeId)
                .doOnNext {
                    if (!it.ingredients.removeIf { i -> i.id == ingredientId })
                        throw IllegalArgumentException("Ingredient was not found")
                }
                .thenReturn(Unit)

//        val recipeOpt = recipeRepository.findById(recipeId)
//
//        if (recipeOpt.isPresent && recipeOpt.get().ingredients.firstOrNull { it.id == ingredientId } != null) {
//
//            recipeOpt.get().ingredients.removeIf { it.id == ingredientId }
//
//            recipeRepository.save(recipeOpt.get())
//
//        } else
//            throw RuntimeException("Internal Error")
//
//        return Mono.empty()
    }
}