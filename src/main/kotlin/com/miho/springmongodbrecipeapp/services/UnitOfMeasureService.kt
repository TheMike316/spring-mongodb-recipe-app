package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand

interface UnitOfMeasureService {

    fun findByUnit(unit: String): UnitOfMeasureCommand?

    fun listAllUoms(): Set<UnitOfMeasureCommand>
}