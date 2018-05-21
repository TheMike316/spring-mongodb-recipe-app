package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.domain.Category
import com.miho.springmongodbrecipeapp.domain.Ingredient
import com.miho.springmongodbrecipeapp.domain.Recipe
import org.springframework.stereotype.Component

@Component
class RecipeCommandToRecipe(val noteConverter: NotesCommandToNotes,
                            val ingredientConverter: IngredientCommandToIngredient,
                            val categoryConverter: CategoryCommandToCategory) : KotlinConverter<RecipeCommand?, Recipe?> {


    @Synchronized
    override fun convert(source: RecipeCommand?): Recipe? {

        if (source == null)
            return null

        return Recipe(description = source.description, prepTime = source.prepTime, cookTime = source.cookTime,
                servings = source.servings, source = source.source, url = source.url, directions = source.directions,
                notes = noteConverter.convert(source.notes)
                , ingredients = source.ingredients.asSequence().map { ingredientConverter.convert(it) }
                .filter { it != null }
                .map { it as Ingredient }
                .toMutableSet(),
                difficulty = source.difficulty,
                categories = source.categories.asSequence().map { categoryConverter.convert(it) }
                        .filter { it != null }
                        .map { it as Category }
                        .toMutableSet(),
                id = source.id)
    }
}