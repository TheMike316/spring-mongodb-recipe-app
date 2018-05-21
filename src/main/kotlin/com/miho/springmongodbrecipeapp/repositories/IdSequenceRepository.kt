package com.miho.springmongodbrecipeapp.repositories

import com.miho.springmongodbrecipeapp.domain.IdSequence

interface IdSequenceRepository {

    fun getNextId(sequenceKey: String): String

    fun save(idSequence: IdSequence)

}