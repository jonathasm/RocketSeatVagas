package backend.vagas.repository

import backend.vagas.model.Candidate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CandidateRepository : JpaRepository<Candidate?, UUID?> {
    fun findByUsernameOrEmail(username: String?, email: String?): Candidate?

    fun findByUsername(username: String?): Candidate?
}
