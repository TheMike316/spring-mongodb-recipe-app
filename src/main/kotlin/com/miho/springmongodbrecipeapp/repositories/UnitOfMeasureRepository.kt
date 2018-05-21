package com.miho.springmongodbrecipeapp.repositories

import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.springframework.data.repository.CrudRepository

interface UnitOfMeasureRepository : CrudRepository<UnitOfMeasure, String> {

    fun findByUnit(unit: String): UnitOfMeasure?
}