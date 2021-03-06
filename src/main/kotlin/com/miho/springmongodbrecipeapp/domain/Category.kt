package com.miho.springmongodbrecipeapp.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Category(var description: String = "",

               val recipes: MutableSet<Recipe> = mutableSetOf(),

               @field: Id
               var id: String = "")