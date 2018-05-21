package com.miho.springmongodbrecipeapp.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class IdSequence(@field: Id val sequenceKey: String, val nextId: Long = 0)