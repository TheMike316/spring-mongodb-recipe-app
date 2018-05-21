package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.repositories.UnitOfMeasureRepository
import org.springframework.stereotype.Service

@Service
class UnitOfMeasureServiceImpl(private val unitOfMeasureRepository: UnitOfMeasureRepository,
                               private val uomToCommand: UnitOfMeasureToUnitOfMeasureCommand) : UnitOfMeasureService {


    override fun findByUnit(unit: String): UnitOfMeasureCommand? = unitOfMeasureRepository.findByUnit(unit).let {
        uomToCommand.convert(it) ?: throw RuntimeException("No Unit found for $unit")
    }

    override fun listAllUoms() = unitOfMeasureRepository.findAll().map(uomToCommand::convert).map { it as UnitOfMeasureCommand }.toSet()


}