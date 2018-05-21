package com.miho.springmongodbrecipeapp.repositories

import com.miho.springmongodbrecipeapp.domain.Category
import org.springframework.data.repository.Repository

interface CategoryRepository : Repository<Category, String> {

    fun findByDescription(description: String): Category?

    fun save(category: Category): Category

}