package com.miho.springmongodbrecipeapp.domain

import org.springframework.data.mongodb.core.mapping.DBRef
import java.math.BigDecimal
import java.util.*

class Ingredient(var description: String = "",

                 var amount: BigDecimal = BigDecimal.ZERO,

                 @DBRef
                 var unitOfMeasure: UnitOfMeasure? = null,

//               Ingredients are not stored as separate documents but as a list item inside their recipes
//               Because of this they don't actually have an ID inside Mongo
                 var id: String = UUID.randomUUID().toString()) {


    override fun toString() = "$amount ${unitOfMeasure?.unit} of $description"

}
