package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.IngredientCommand

interface IngredientService {

    fun findByRecipeIdAndIngredientId(recipeId: String, ingredientId: String): IngredientCommand?

    fun saveOrUpdateIngredient(ingredientCommand: IngredientCommand, recipeId: String): IngredientCommand

    fun deleteById(recipeId: String, ingredientId: String)
}