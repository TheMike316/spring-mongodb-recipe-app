package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.springframework.stereotype.Component

@Component
class UnitOfMeasureCommandToUnitOfMeasure : KotlinConverter<UnitOfMeasureCommand?, UnitOfMeasure?> {

    @Synchronized
    override fun convert(source: UnitOfMeasureCommand?): UnitOfMeasure? {

        if (source == null)
            return null

        return UnitOfMeasure(source.unit, source.id)

    }
}