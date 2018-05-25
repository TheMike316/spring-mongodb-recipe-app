package com.miho.springmongodbrecipeapp.repositories.reactive

import com.miho.springmongodbrecipeapp.domain.Category
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
class CategoryReactiveRepositoryIT {

    @Autowired
    private lateinit var categoryReactiveRepository: CategoryReactiveRepository

    @Autowired
    private lateinit var testDataHelper: TestDataHelper

    @After
    fun tearDown() {
        testDataHelper.resetCategories()
    }

    @Test
    fun testFindAll() {
        val count = categoryReactiveRepository.findAll()
                .count()
                .block()

        assertEquals(5L, count)
    }

    @Test
    fun testFindAllFilter() {
        val count = categoryReactiveRepository.findAll()
                .filter { it.description == "American" }
                .count()
                .block()

        assertEquals(1L, count)
    }

    @Test
    fun testInsert() {
        val categoriesBefore = categoryReactiveRepository.findAll().toIterable()

        val newCategory = Category(description = "asdfffffffffffasdfasdf")

        val insertMono = categoryReactiveRepository.insert(newCategory)

        val categoriesBeforeBlock = categoryReactiveRepository.findAll().toIterable()

        assertFalse(categoriesBeforeBlock.map { it.description }.contains(newCategory.description))
        assertTrue(categoriesBefore.count() == categoriesBeforeBlock.count())

        insertMono.block()

        val categoriesAfter = categoryReactiveRepository.findAll().toIterable()
        assertTrue(categoriesAfter.map { it.description }.contains(newCategory.description))
    }

    @Test
    fun testDeleteAll() {
        val countBefore = categoryReactiveRepository.findAll().count().block()!!
        assertTrue(countBefore > 0)

        val deleteMono = categoryReactiveRepository.deleteAll()

        assertEquals(countBefore, categoryReactiveRepository.findAll().count().block()!!)

        deleteMono.block()

        assertEquals(0L, categoryReactiveRepository.findAll().count().block()!!)
    }

    @Test
    fun testFindbyDescription() {
        val mexicanCategory = "Mexican"

        val category = categoryReactiveRepository.findByDescription(mexicanCategory).block()

        assertTrue(category != null)
        assertEquals(mexicanCategory, category!!.description)
    }
}