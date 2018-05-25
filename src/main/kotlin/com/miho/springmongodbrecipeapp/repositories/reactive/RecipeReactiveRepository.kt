package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.Recipe
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface RecipeReactiveRepository : ReactiveMongoRepository<Recipe, String>