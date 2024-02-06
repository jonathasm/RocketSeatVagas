package backend.vagas.repository

import backend.vagas.model.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository : JpaRepository<Company, UUID> {
    fun findByUsernameOrEmail(username: String?, email: String?): Company?
    fun findByUsername(username: String?): Company?

}