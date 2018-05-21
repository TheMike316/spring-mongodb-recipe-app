package com.miho.springmongodbrecipeapp.commands

import com.miho.springmongodbrecipeapp.domain.Difficulty
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RecipeCommand(

        @get: [NotBlank Size(min = 3, max = 255)]
        var description: String = "",

        @get: [Min(1) Max(999)]
        var prepTime: Int = 0,

        @get: [Min(1) Max(999)]
        var cookTime: Int = 0,

        @get: [Min(1) Max(100)]
        var servings: Int = 0,

        var source: String = "",

        @get: URL
        var url: String = "",

        @get: NotBlank
        var directions: String = "",
        var notes: NotesCommand? = null,
        var ingredients: MutableSet<IngredientCommand> = mutableSetOf(),
        var difficulty: Difficulty = Difficulty.EASY,
        var categories: MutableSet<CategoryCommand> = mutableSetOf(),
        var image: ByteArray = byteArrayOf(),
        var id: String = "")