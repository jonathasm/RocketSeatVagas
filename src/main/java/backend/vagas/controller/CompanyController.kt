package backend.vagas.controller

import backend.vagas.model.Company
import backend.vagas.service.CompanyService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/company")
class CompanyController(val companyService: CompanyService) {

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    fun create(@Valid @RequestBody company: Company): ResponseEntity<Company>? {
        return this.companyService.createCompany(company)
    }
}