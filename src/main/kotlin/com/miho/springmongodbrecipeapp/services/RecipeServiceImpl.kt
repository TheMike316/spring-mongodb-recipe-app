package com.miho.springmongodbrecipeapp.services

import com.miho.springmongodbrecipeapp.commands.RecipeCommand
import com.miho.springmongodbrecipeapp.converters.RecipeCommandToRecipe
import com.miho.springmongodbrecipeapp.converters.RecipeToRecipeCommand
import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import com.miho.springmongodbrecipeapp.repositories.reactive.RecipeReactiveRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Transactional
class RecipeServiceImpl(private val recipeRepository: RecipeReactiveRepository,
                        private val recipeToCommand: RecipeToRecipeCommand,
                        private val commandToRecipe: RecipeCommandToRecipe) : RecipeService {

    //TODO error handling when no Recipe could be found
    override fun findById(id: String) = recipeRepository.findById(id)
            .map { recipeToCommand.convert(it) ?: throw NotFoundException("Recipe was not found") }


    override fun getAllRecipes() = recipeRepository.findAll()
            .map { recipeToCommand.convert(it) ?: throw IllegalStateException() }

    override fun saveAll(recipes: Flux<RecipeCommand>): Flux<RecipeCommand> {
        return recipeRepository.saveAll(recipes.map {
            commandToRecipe.convert(it) ?: throw IllegalArgumentException("Recipes must not be null")
        })
                .map { recipeToCommand.convert(it) ?: throw RuntimeException("Internal error") }
    }

    override fun saveRecipe(recipeCommand: RecipeCommand?): Mono<RecipeCommand> =
            recipeRepository.save(commandToRecipe.convert(recipeCommand)
                    ?: throw IllegalArgumentException("RecipeCommand must not be null"))
                    .map { recipeToCommand.convert(it) ?: throw RuntimeException("Internal error") }

    override fun deleteById(id: String) = recipeRepository.deleteById(id).doOnError { throw it }
}