package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import com.miho.springmongodbrecipeapp.testutils.TestDataHelper
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UnitOfMeasureReactiveRepositoryIT {

    @Autowired
    private lateinit var unitOfMeasureReactiveRepository: UnitOfMeasureReactiveRepository

    @Autowired
    private lateinit var testDataHelper: TestDataHelper

    @After
    fun tearDown() {
        testDataHelper.resetUnitOfMeasures()
    }

    @Test
    fun testFindAll() {
        val count = unitOfMeasureReactiveRepository.findAll()
                .count()
                .block()

        assertEquals(9L, count)
    }

    @Test
    fun testFindAllFilter() {
        val count = unitOfMeasureReactiveRepository.findAll()
                .filter { "Tablespoon" == it.unit || "Dash" == it.unit || "Each" == it.unit }
                .count()
                .block()

        assertEquals(3L, count)
    }

    @Test
    fun testInsert() {
        val uomsBefore = unitOfMeasureReactiveRepository.findAll().toIterable()

        val newUom = UnitOfMeasure(unit = "ASSDUFF")

        val insertMono = unitOfMeasureReactiveRepository.insert(newUom)

        val uomsBeforeBlock = unitOfMeasureReactiveRepository.findAll().toIterable()

        assertEquals(uomsBefore.count(), uomsBeforeBlock.count())
        assertFalse(uomsBeforeBlock.map { it.unit }.contains(newUom.unit))

        insertMono.block()

        val uomsAfterBlock = unitOfMeasureReactiveRepository.findAll().toIterable()

        assertTrue(uomsAfterBlock.map { it.unit }.contains(newUom.unit))
    }

    @Test
    fun testDeleteAll() {
        val countBefore = unitOfMeasureReactiveRepository.findAll().count().block()!!
        assertTrue(countBefore > 0)

        val deleteMono = unitOfMeasureReactiveRepository.deleteAll()

        assertEquals(countBefore, unitOfMeasureReactiveRepository.findAll().count().block()!!)

        deleteMono.block()

        assertEquals(0L, unitOfMeasureReactiveRepository.findAll().count().block()!!)
    }

    @Test
    fun testFindByUnit() {
        val unit = "Dash"

        val uom = unitOfMeasureReactiveRepository.findByUnit(unit).block()

        assertTrue(uom != null)
        assertEquals(unit, uom!!.unit)
    }
}