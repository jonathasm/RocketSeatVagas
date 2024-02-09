package backend.vagas.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.function.Consumer

@ControllerAdvice
class HandlerAdviceException {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<Map<String?, String>> {
        val errors: MutableMap<String?, String> = HashMap()
        e.bindingResult.fieldErrors.forEach(Consumer { error: FieldError ->
            errors[error.defaultMessage] = error.field
        })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun methodArgumentNotValidException(e: HttpRequestMethodNotSupportedException): ResponseEntity<Map<String, String>> {
        val errors: MutableMap<String, String> = HashMap()
        errors["ERROR! "] = e.localizedMessage

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserException::class)
    fun userNotFoundException(e: UserException): ResponseEntity<Map<String, String>> {
        val errors: MutableMap<String, String> = HashMap()
        errors["ERROR! "] = e.localizedMessage

        return ResponseEntity(errors, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun usernameNotFoundException(e: UsernameNotFoundException): ResponseEntity<Map<String, String>> {
        val errors: MutableMap<String, String> = HashMap()
        errors["ERROR! "] = e.localizedMessage

        return ResponseEntity(errors, HttpStatus.UNAUTHORIZED)
    }

}

