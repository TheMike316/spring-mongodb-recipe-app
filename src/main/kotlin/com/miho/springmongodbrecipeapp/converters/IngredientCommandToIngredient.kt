package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.domain.Ingredient
import org.springframework.stereotype.Component

@Component
class IngredientCommandToIngredient(val unitConverter: UnitOfMeasureCommandToUnitOfMeasure) : KotlinConverter<IngredientCommand?, Ingredient?> {

    @Synchronized
    override fun convert(source: IngredientCommand?): Ingredient? {

        if (source == null)
            return null

        return Ingredient(description = source.description, amount = source.amount, unitOfMeasure = unitConverter.convert(source.unitOfMeasure),
                id = source.id /*,recipe = Recipe(id = source.recipeId)*/)
    }
}