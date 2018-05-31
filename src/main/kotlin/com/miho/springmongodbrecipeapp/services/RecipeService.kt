package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RecipeService {

    fun getAllRecipes(): Flux<RecipeCommand>

    fun findById(id: String): Mono<RecipeCommand>

    fun saveAll(recipes: Flux<RecipeCommand>): Flux<RecipeCommand>

    fun saveRecipe(recipeCommand: RecipeCommand?): Mono<RecipeCommand>

    fun deleteById(id: String): Mono<Void>
}