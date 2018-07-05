package com.miho.springmongodbrecipeapp.controllers

import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class ControllerExceptionHandler {

//    TODO fix with webflux
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException::class)
//    fun handleNotFoundException(e: Exception): ModelAndView {
//
//        val modelAndView = ModelAndView("404error")
//
//        modelAndView.addObject("exception", e)
//
//        return modelAndView
//
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberFormatException::class)
//    fun handleNumberFormatException(e: Exception): ModelAndView {
//
//        val modelAndView = ModelAndView("400error")
//
//        modelAndView.addObject("exception", e)
//
//        return modelAndView
//
//    }
}