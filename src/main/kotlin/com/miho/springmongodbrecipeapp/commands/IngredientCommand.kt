package com.miho.springmongodbrecipeapp.commands

import java.math.BigDecimal

data class IngredientCommand(var id: String = "", var description: String = "", var amount: BigDecimal = BigDecimal.ZERO, var unitOfMeasure: UnitOfMeasureCommand? = null) {

    override fun toString() = "$amount ${unitOfMeasure?.unit} of $description"
}