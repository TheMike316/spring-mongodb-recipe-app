package com.miho.springmongodbrecipeapp.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["com.miho.springmongodbrecipeapp.repositories"])
class MongoConfig