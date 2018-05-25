package com.miho.springmongodbrecipeapp.repositories

import com.miho.springmongodbrecipeapp.repositories.reactive.CategoryReactiveRepository
import com.miho.springmongodbrecipeapp.testutils.TestDataHelper
import org.junit.After
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

//    @Test
//    fun test
//    fun test
}