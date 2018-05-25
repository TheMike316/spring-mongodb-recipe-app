package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.Category
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CategoryReactiveRepository : ReactiveMongoRepository<Category, String>