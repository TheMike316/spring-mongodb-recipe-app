package com.miho.springmongodbrecipeapp.repositories

import com.miho.springmongodbrecipeapp.domain.IdSequence
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class IdSequenceRepositoryImpl(val mongoOperations: MongoOperations) : IdSequenceRepository {


    override fun getNextId(sequenceKey: String): String {
        //get sequence id
        val query = Query(Criteria.where("_id").`is`(sequenceKey))

        //create a sequence if it didn't exist before
        if (mongoOperations.find(query, IdSequence::class.java).isEmpty())
            save(IdSequence(sequenceKey))

        //increase sequence id by 1
        val update = Update()
        update.inc("nextId", 1)

        //return new increased id
        val options = FindAndModifyOptions()
        options.returnNew(true)

        //this is the magic happened.
        val seqId = mongoOperations.findAndModify(query, update, options, IdSequence::class.java)
                ?: throw RuntimeException("Unable to get sequence id for key : $sequenceKey")

        return seqId.nextId.toString()
    }

    override fun save(idSequence: IdSequence) {
        mongoOperations.insert(idSequence)
    }
}