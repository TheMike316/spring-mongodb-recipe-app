package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UnitOfMeasureReactiveRepository : ReactiveMongoRepository<UnitOfMeasure, String> {

    fun findByUnit(unit: String): Mono<UnitOfMeasure?>
}

