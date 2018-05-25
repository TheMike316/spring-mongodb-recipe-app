package com.miho.springmongodbrecipeapp.repositories

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private lateinit var repository: UnitOfMeasureRepository

    @Test
    fun findByDescription() {

        assertNotEquals(null, repository)

        val unit = repository.findByUnit("Teaspoon")

        assertEquals("Teaspoon", unit?.unit)
    }

    @Test
    fun findByDescriptionCup() {

        assertNotEquals(null, repository)

        val unit = repository.findByUnit("Cup")

        assertEquals("Cup", unit?.unit)
    }

}