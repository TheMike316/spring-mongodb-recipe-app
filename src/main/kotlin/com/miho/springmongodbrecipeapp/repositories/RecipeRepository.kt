package com.miho.springmongodbrecipeapp.repositories

import com.miho.springmongodbrecipeapp.domain.Recipe
import org.springframework.data.repository.CrudRepository

interface RecipeRepository : CrudRepository<Recipe, String>
