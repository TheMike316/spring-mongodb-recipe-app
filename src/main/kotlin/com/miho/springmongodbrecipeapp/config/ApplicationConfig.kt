package com.miho.springmongodbrecipeapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
class ApplicationConfig {

    @Bean
    fun placeholderConfigurer() = PropertySourcesPlaceholderConfigurer()
}