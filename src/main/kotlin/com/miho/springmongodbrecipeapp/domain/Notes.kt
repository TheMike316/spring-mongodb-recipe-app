package com.miho.springmongodbrecipeapp.domain

import org.springframework.data.annotation.Id

class Notes(/*var recipe: Recipe? = null,*/

        var recipeNotes: String = "",

        @field: Id
        var id: String = "")