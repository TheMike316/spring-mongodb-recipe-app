package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.thymeleaf.exceptions.TemplateInputException

@ControllerAdvice
class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class, TemplateInputException::class)
    fun handleNotFoundException(e: Exception, model: Model): String {

        model.addAttribute("exception", e)

        return "404error"

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException(e: Exception, model: Model): String {

        model.addAttribute("exception", e)

        return "400error"

    }
}