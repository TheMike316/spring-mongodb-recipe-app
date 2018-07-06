package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.converters.IngredientCommandToIngredient
import com.miho.springmongodbrecipeapp.converters.IngredientToIngredientCommand
import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IngredientServiceImpl(private val recipeRepository: RecipeReactiveRepository,
                            private val ingredientToCommand: IngredientToIngredientCommand,
                            private val commandToIngredient: IngredientCommandToIngredient) : IngredientService {

    override fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): Mono<IngredientCommand> {
        return recipeRepository.findById(recipeId)
                .map { it.ingredients.find { i -> ingredientId == i.id } }
                .map { ingredientToCommand.convertAndAddRecipeId(it, recipeId) ?: throw  RuntimeException("ingredient was not found") }
    }

    override fun saveOrUpdateIngredient(ingredientCommand: IngredientCommand, recipeId: String): Mono<IngredientCommand> {
        val ingredient = commandToIngredient.convert(ingredientCommand)
                ?: throw  IllegalArgumentException("ingredient is null")

        return recipeRepository.findById(recipeId)
                .doOnNext {
                    it.ingredients.removeIf { i -> ingredient.id == i.id }
                    it.addIngredient(ingredient)
                    recipeRepository.save(it).block()
                }
                .map { it.ingredients.find { i -> ingredient.id == i.id } }
                .map { ingredientToCommand.convertAndAddRecipeId(it, recipeId) ?: throw RuntimeException("Internal Error!") }
    }

    //TODO error handling when recipe could not be found
    override fun deleteById(recipeId: String, ingredientId: String): Mono<Unit> {
        return recipeRepository.findById(recipeId)
                .doOnNext {
                    if (!it.ingredients.removeIf { i -> i.id == ingredientId })
                        throw RuntimeException("Ingredient was not found")

                    recipeRepository.save(it).block()
                }
                .thenReturn(Unit)
    }


}