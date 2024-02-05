package backend.vagas.controller

import backend.vagas.security.AuthCompany
import backend.vagas.security.AuthCompanyDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authCompany: AuthCompany) {
    @PostMapping
    fun auth(@RequestBody authCompanyDto: AuthCompanyDto): String {
        return authCompany.authExecute(authCompanyDto)
    }
}