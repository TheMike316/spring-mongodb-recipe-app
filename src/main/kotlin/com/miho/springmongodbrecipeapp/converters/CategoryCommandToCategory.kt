package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.CategoryCommand
import com.miho.springmongodbrecipeapp.domain.Category
import org.springframework.stereotype.Component

@Component
class CategoryCommandToCategory : KotlinConverter<CategoryCommand?, Category?> {

    @Synchronized
    override fun convert(source: CategoryCommand?): Category? {

        if (source == null)
            return null

        return Category(description = source.description, id = source.id)


    }
}