package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UnitOfMeasureReactiveRepository : ReactiveMongoRepository<UnitOfMeasure, String> {
}