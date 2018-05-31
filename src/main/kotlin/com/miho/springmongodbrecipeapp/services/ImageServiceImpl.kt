package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono

@Service
class ImageServiceImpl(private val recipeRepository: RecipeReactiveRepository) : ImageService {

    override fun saveImageFile(recipeId: String, image: MultipartFile?): Mono<Unit> {

        if (image == null)
            throw RuntimeException("Internal Error")

        return recipeRepository.findById(recipeId)
                .doOnNext {
                    it.image = image.bytes
                    recipeRepository.save(it)
                            .doOnError { throw RuntimeException("Internal Error") }
                }
                .thenReturn(Unit)
    }
}