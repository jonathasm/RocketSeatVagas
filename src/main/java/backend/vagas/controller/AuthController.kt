package backend.vagas.controller

import backend.vagas.security.Auths
import backend.vagas.security.AuthsDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val auths: Auths) {
    @PostMapping
    fun auth(@RequestBody authsDto: AuthsDto): String {
        return "null"
    }
}