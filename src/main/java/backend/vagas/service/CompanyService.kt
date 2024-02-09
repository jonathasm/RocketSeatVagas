package backend.vagas.service

import backend.vagas.controller.exception.UserException
import backend.vagas.model.Company
import backend.vagas.repository.CompanyRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CompanyService(val companyRepository: CompanyRepository) {

    fun createCompany(company: Company): ResponseEntity<Company> {
        companyRepository.findByUsernameOrEmail(company.username, company.email).let {
            if (it != null) throw UserException()
        }
        company.password = BCryptPasswordEncoder().encode(company.password)
        return ResponseEntity.ok(companyRepository.save(company))
    }
}
