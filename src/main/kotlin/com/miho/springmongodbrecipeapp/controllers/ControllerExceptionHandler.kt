package com.miho.springmongodbrecipeapp.controllers

import com.miho.springmongodbrecipeapp.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: Exception): ModelAndView {

        val modelAndView = ModelAndView("404error")

        modelAndView.addObject("exception", e)

        return modelAndView

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException::class)
    fun handleNumberFormatException(e: Exception): ModelAndView {

        val modelAndView = ModelAndView("400error")

        modelAndView.addObject("exception", e)

        return modelAndView

    }
}