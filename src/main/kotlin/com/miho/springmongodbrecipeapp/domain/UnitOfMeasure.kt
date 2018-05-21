package com.miho.springmongodbrecipeapp.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class UnitOfMeasure(var unit: String = "",

                    @field: Id
                    var id: String = "")
