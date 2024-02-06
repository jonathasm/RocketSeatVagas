package backend.vagas.service

import backend.vagas.model.Company
import backend.vagas.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CompanyService(val companyRepository: CompanyRepository) {


    @Autowired
    lateinit var passwordEncoder: PasswordEncoder


    fun createCompany(company: Company): ResponseEntity<Company> {
        runCatching { companyRepository.findByUsernameOrEmail(company.username, company.email) }
            .onFailure {
                throw Exception("User already exists")
            }

        company.password = BCryptPasswordEncoder().encode(company.password)
        return ResponseEntity.ok(companyRepository.save(company))
    }
}
