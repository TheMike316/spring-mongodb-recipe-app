package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.commands.IngredientCommand
import com.miho.springmongodbrecipeapp.commands.UnitOfMeasureCommand
import com.miho.springmongodbrecipeapp.services.IngredientService
import com.miho.springmongodbrecipeapp.services.RecipeService
import com.miho.springmongodbrecipeapp.services.UnitOfMeasureService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/recipe")
class IngredientController(private val recipeService: RecipeService,
                           private val ingredientService: IngredientService, private val uomService: UnitOfMeasureService) {

    @GetMapping("/{id}/ingredients")
    fun getIngredientList(@PathVariable id: String, model: Model): String {

        model.addAttribute("recipe", recipeService.findById(id))

        return "recipe/ingredient/list"
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/show")
    fun showById(@PathVariable recipeId: String, @PathVariable ingredientId: String, model: Model): String {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId))

        return "recipe/ingredient/show"
    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/update")
    fun updateIngredient(@PathVariable recipeId: String, @PathVariable ingredientId: String, model: Model): String {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId))

        model.addAttribute("uomList", uomService.listAllUoms())

        return "recipe/ingredient/ingredientform"

    }

    @GetMapping("/{recipeId}/ingredient/new")
    fun newIngredient(@PathVariable recipeId: String, model: Model): String {

        val recipeCommand = recipeService.findById(recipeId).block()
//		TODO raise Exception if null

        if (recipeCommand != null)
            model.addAttribute("ingredient", IngredientCommand(unitOfMeasure = UnitOfMeasureCommand()))

        model.addAttribute("uomList", uomService.listAllUoms())

        return "recipe/ingredient/ingredientform"

    }

    @PostMapping("/{recipeId}/ingredient")
    fun saveOrUpdate(@ModelAttribute ingredient: IngredientCommand, @PathVariable recipeId: String): String {

        //TODO refactor thymeleaf templates to enable end-to-end reactive process
        val savedIngredient = ingredientService.saveOrUpdateIngredient(ingredient, recipeId).block()!!

        return "redirect:/recipe/$recipeId/ingredient/${savedIngredient.id}/show"

    }

    @GetMapping("/{recipeId}/ingredient/{ingredientId}/delete")
    fun delete(@PathVariable recipeId: String, @PathVariable ingredientId: String): String {

        ingredientService.deleteById(recipeId, ingredientId)

        return "redirect:/recipe/$recipeId/ingredients"
    }
}