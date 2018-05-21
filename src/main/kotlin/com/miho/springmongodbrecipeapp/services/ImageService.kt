package com.miho.springmongodbrecipeapp.services

import org.springframework.web.multipart.MultipartFile

interface ImageService {

    fun saveImageFile(recipeId: String, image: MultipartFile?)
}