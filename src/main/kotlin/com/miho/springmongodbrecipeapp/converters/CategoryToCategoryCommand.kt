package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.CategoryCommand
import com.miho.springmongodbrecipeapp.domain.Category
import org.springframework.stereotype.Component

@Component
class CategoryToCategoryCommand : KotlinConverter<Category?, CategoryCommand?> {

    @Synchronized
    override fun convert(source: Category?): CategoryCommand? {

        if (source == null)
            return null

        return CategoryCommand(source.id, source.description)


    }
}