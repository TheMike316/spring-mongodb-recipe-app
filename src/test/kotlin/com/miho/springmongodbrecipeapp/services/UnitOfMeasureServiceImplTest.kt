package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import com.miho.springmongodbrecipeapp.repositories.reactive.UnitOfMeasureReactiveRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import reactor.core.publisher.Flux
import org.mockito.Mockito.`when` as mockitoWhen

class UnitOfMeasureServiceImplTest {

    private lateinit var uomService: UnitOfMeasureService

    @Mock
    private lateinit var uomRepository: UnitOfMeasureReactiveRepository

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        val uomToCommand = UnitOfMeasureToUnitOfMeasureCommand()

        uomService = UnitOfMeasureServiceImpl(uomRepository, uomToCommand)
    }

    @Test
    fun testListAllUoms() {
//		given
        val uom1 = UnitOfMeasure(id = "1")
        val uom2 = UnitOfMeasure(id = "2")
        val uom3 = UnitOfMeasure(id = "3")

        val uoms = arrayOf(uom1, uom2, uom3)

//		when
        mockitoWhen(uomRepository.findAll()).thenReturn(Flux.just(*uoms))

        val uomCommands = uomService.listAllUoms().toIterable()

//		then
        assertEquals(uoms.size, uomCommands.count())
        verify(uomRepository, times(1)).findAll()

    }
}