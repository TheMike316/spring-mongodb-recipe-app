package com.miho.springmongodbrecipeapp.converters

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import org.springframework.stereotype.Component

@Component
class UnitOfMeasureToUnitOfMeasureCommand : KotlinConverter<UnitOfMeasure?, UnitOfMeasureCommand?> {

    @Synchronized
    override fun convert(source: UnitOfMeasure?): UnitOfMeasureCommand? {

        if (source == null)
            return null

        return UnitOfMeasureCommand(source.id, source.unit)
    }
}