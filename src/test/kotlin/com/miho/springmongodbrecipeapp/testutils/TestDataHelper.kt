package com.miho.springmongodbrecipeapp.testutils

import com.miho.springmongodbrecipeapp.bootstrap.RecipeDataBootstrap
import com.miho.springmongodbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository
import org.springframework.stereotype.Component
import org.springframework.test.util.ReflectionTestUtils

@Component
class TestDataHelper(private val recipeDataBootstrap: RecipeDataBootstrap,
                     private val unitOfMeasureReactiveRepository: UnitOfMeasureReactiveRepository) {

    fun resetUnitOfMeasures() {
        unitOfMeasureReactiveRepository.deleteAll().block()

        ReflectionTestUtils.invokeMethod<Unit>(recipeDataBootstrap, "loadUnits")
    }
}