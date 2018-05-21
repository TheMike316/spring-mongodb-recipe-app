package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ImageServiceImpl(private val recipeRepository: RecipeRepository) : ImageService {

    @Transactional
    override fun saveImageFile(recipeId: String, image: MultipartFile?) {

        if (image == null)
            throw RuntimeException("Internal Error")

        val recipeOpt = recipeRepository.findById(recipeId)

        if (!recipeOpt.isPresent)
            throw NotFoundException("Recipe not found")

        recipeOpt.get().image = image.bytes

        recipeRepository.save(recipeOpt.get())

    }
}