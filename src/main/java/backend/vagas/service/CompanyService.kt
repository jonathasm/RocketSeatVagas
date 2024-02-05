package backend.vagas.service

import backend.vagas.controller.exception.UserNotFoundException
import backend.vagas.model.Company
import backend.vagas.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class CompanyService(val companyRepository: CompanyRepository) {


    @Autowired
    lateinit var passwordEncoder: PasswordEncoder


    fun createCompany(company: Company): ResponseEntity<Company> {
        try {
            companyRepository.findByUsernameOrEmail(company.username, company.email)
                ?.stream()
                ?.findAny()
                ?.ifPresent(Consumer {
                    throw UserNotFoundException()
                })
        } catch (e: Exception) {
            println(e.message)
            return ResponseEntity.badRequest().build()
        }
        company.password = passwordEncoder.encode(company.password)
        return ResponseEntity.ok(companyRepository.save(company))
    }
}
