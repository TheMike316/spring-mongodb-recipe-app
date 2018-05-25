package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UnitOfMeasureService {

    fun findByUnit(unit: String): Mono<UnitOfMeasureCommand?>

    fun listAllUoms(): Flux<UnitOfMeasureCommand>
}