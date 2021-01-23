package com.github.urlshorten

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ValidationException

@RestControllerAdvice
class ErrorHandler {

    private val logger = LoggerFactory.getLogger(ErrorHandler::class.java)

    @ExceptionHandler
    fun handleException(throwable: Throwable?): ResponseEntity<*> {
        logger.error("Error occurred", throwable)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build<Any>()
    }

    @ExceptionHandler
    fun handleValidationException(exception: ValidationException): ResponseEntity<*> {
        logger.info(exception.message, exception)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.message)
    }
}