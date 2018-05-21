package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.domain.Ingredient
import org.springframework.stereotype.Component

@Component
class IngredientToIngredientCommand(val unitConverter: UnitOfMeasureToUnitOfMeasureCommand) : KotlinConverter<Ingredient?, IngredientCommand?> {

    @Synchronized
    override fun convert(source: Ingredient?): IngredientCommand? {

        if (source == null)
            return null;

        val convertedUnit = unitConverter.convert(source.unitOfMeasure)

        return IngredientCommand(id = source.id /*, recipeId = source.recipe?.id ?: ""*/,
                description = source.description, amount = source.amount, unitOfMeasure = convertedUnit)


    }
}