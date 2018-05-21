package com.miho.springmongodbrecipeapp.converters

import org.springframework.core.convert.converter.Converter

interface KotlinConverter<S, T> : Converter<S, T> {

    override fun convert(source: S): T
}