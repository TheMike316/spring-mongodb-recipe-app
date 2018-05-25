package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.Category
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface CategoryReactiveRepository : ReactiveMongoRepository<Category, String> {

    fun findByDescription(description: String): Mono<Category?>
}