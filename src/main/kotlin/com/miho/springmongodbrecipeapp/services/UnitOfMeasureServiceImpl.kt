package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository
import org.springframework.stereotype.Service

@Service
class UnitOfMeasureServiceImpl(private val unitOfMeasureRepository: UnitOfMeasureReactiveRepository,
                               private val uomToCommand: UnitOfMeasureToUnitOfMeasureCommand) : UnitOfMeasureService {


    override fun findByUnit(unit: String) = unitOfMeasureRepository.findByUnit(unit).map {
        uomToCommand.convert(it) ?: throw NotFoundException("Unit was not found")
    }


    override fun listAllUoms() = unitOfMeasureRepository.findAll().map(uomToCommand::convert).map { it as UnitOfMeasureCommand }


}