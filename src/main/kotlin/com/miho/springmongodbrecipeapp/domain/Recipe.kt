package com.miho.springmongodbrecipeapp.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Recipe(var description: String = "",

             var prepTime: Int = 0,

             var cookTime: Int = 0,

             var servings: Int = 0,

             var source: String = "",

             var url: String = "",

             var directions: String = "",

             var image: ByteArray = byteArrayOf(),

             notes: Notes? = null,

             ingredients: MutableSet<Ingredient> = mutableSetOf(),

             var difficulty: Difficulty = Difficulty.EASY,

             @DBRef
             val categories: MutableSet<Category> = mutableSetOf(),

             @field: Id
             var id: String = "") {

    var notes: Notes? = null


    val ingredients: MutableSet<Ingredient> = mutableSetOf()

    init {

        this.notes = notes

        addIngredients(ingredients)

    }

    fun addIngredient(ingredient: Ingredient) {

//        ingredient.recipe = this

        this.ingredients.add(ingredient)

    }

    fun addIngredients(ingredients: Iterable<Ingredient>) = ingredients.forEach(this::addIngredient)

    fun removeIngredient(ingredient: Ingredient): Boolean {

        return ingredients.remove(ingredient)

//        if (result)
//            ingredient.recipe = null

//            return result

    }

    fun addCategory(category: Category) = categories.add(category)

    fun addCategories(categories: Iterable<Category>) = this.categories.addAll(categories)

    fun removeCategory(category: Category) = categories.remove(category)


}