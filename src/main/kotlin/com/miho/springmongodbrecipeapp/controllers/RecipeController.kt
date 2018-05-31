package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.services.RecipeService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Controller
@RequestMapping("/recipe")
class RecipeController(private val recipeService: RecipeService) {


    @GetMapping("/{id}/show")
    fun showById(@PathVariable id: String, model: Model): String {

        model.addAttribute("recipe", recipeService.findById(id).block())

        return "recipe/show"
    }

    @GetMapping("/new")
    fun newRecipe(model: Model): String {

        model.addAttribute("recipe", RecipeCommand())

        return "recipe/recipeform"

    }

    @PostMapping("")
    fun saveOrUpdate(@Valid @ModelAttribute("recipe") recipe: RecipeCommand, bindingResult: BindingResult): String {

        if (bindingResult.hasErrors())
            return "recipe/recipeform"

        val savedCommand = recipeService.saveRecipe(recipe).block()

        return "redirect:/recipe/${savedCommand?.id}/show"
    }

    @GetMapping("/{id}/update")
    fun updateRecipe(@PathVariable id: String, model: Model): String {

        model.addAttribute("recipe", recipeService.findById(id).block())

        return "recipe/recipeform"
    }

    @GetMapping("/{id}/delete")
    fun deleteRecipe(@PathVariable id: String): String {

        recipeService.deleteById(id).block()

        return "redirect:/"
    }


}