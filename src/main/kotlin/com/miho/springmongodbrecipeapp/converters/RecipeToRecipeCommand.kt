package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.CategoryCommand
import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.commands.NotesCommand
import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.domain.Recipe
import org.springframework.stereotype.Component

@Component
class RecipeToRecipeCommand(val notesConverter: NotesToNotesCommand,
                            val ingredientConverter: IngredientToIngredientCommand, val categoryConverter: CategoryToCategoryCommand) : KotlinConverter<Recipe?, RecipeCommand?> {

    @Synchronized
    override fun convert(source: Recipe?): RecipeCommand? {

        if (source == null)
            return null

        val notes = notesConverter.convert(source.notes) ?: NotesCommand("", "")

        return RecipeCommand(source.description, source.prepTime, source.cookTime, source.servings, source.source, source.url, source.directions,
                notes, source.ingredients.asSequence().map { ingredientConverter.convert(it) }
                .filter { it != null }
                .map { it as IngredientCommand }
                .toMutableSet(),
                source.difficulty, source.categories.asSequence().map { categoryConverter.convert(it) }
                .filter { it != null }
                .map { it as CategoryCommand }
                .toMutableSet(), source.image, source.id)


    }
}