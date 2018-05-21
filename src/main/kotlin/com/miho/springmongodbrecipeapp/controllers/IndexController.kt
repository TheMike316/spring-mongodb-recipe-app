package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.services.RecipeService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(private val recipeService: RecipeService) {

    @GetMapping("", "/", "/index")
    fun getIndexPage(model: Model): String {

        model.addAttribute("recipes", recipeService.getAllRecipes())

        return "index"
    }
}