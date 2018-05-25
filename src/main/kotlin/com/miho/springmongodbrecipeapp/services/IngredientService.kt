package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import reactor.core.publisher.Mono

interface IngredientService {

    fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): Mono<IngredientCommand>

    fun saveOrUpdateIngredient(ingredientCommand: IngredientCommand, recipeId: String): Mono<IngredientCommand>

    fun deleteById(recipeId: String, ingredientId: String): Mono<Unit>
}