package com.miho.springmongodbrecipeapp.services

import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono

interface ImageService {

    fun saveImageFile(recipeId: String, image: MultipartFile?): Mono<Unit>
}