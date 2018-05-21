package com.miho.springmongodbrecipeapp.mongo

import com.miho.springmongodbrecipeapp.domain.UnitOfMeasure
import com.miho.springmongodbrecipeapp.repositories.IdSequenceRepository
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent
import org.springframework.stereotype.Component

@Component
class UnitOfMeasureMongoSaveListener(private val idSequenceRepository: IdSequenceRepository) : AbstractMongoEventListener<UnitOfMeasure>() {

    override fun onBeforeSave(event: BeforeSaveEvent<UnitOfMeasure>) {
        val document = event.document ?: throw IllegalArgumentException()

        if (document.getString("_id") == "")
            document["_id"] = idSequenceRepository.getNextId("uom")
    }

}