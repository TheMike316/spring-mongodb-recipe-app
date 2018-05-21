package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.RecipeCommand

interface RecipeService {

    fun getAllRecipes(): Set<RecipeCommand>

    fun findById(id: String): RecipeCommand?

    fun saveAll(recipes: Iterable<RecipeCommand>): Iterable<RecipeCommand>

    fun saveRecipe(recipeCommand: RecipeCommand?): RecipeCommand?

    fun deleteById(id: String)
}