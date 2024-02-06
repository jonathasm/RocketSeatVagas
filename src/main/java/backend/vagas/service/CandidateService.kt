package backend.vagas.service

import backend.vagas.controller.exception.UserNotFoundException
import backend.vagas.model.Candidate
import backend.vagas.repository.CandidateRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CandidateService(private val candidateRepository: CandidateRepository) {
    fun createCandidate(candidate: Candidate): ResponseEntity<Candidate> {

        val candidateOrFail: (Candidate) -> Candidate? = {
            candidateRepository.findByUsernameOrEmail(it.username, it.email)
        }
        runCatching { candidateOrFail.invoke(candidate) }
            .onFailure {
                throw UserNotFoundException()
            }
        BCryptPasswordEncoder().encode(candidate.password).let { candidate.password = it }

        return ResponseEntity.ok(candidateRepository.save(candidate))
    }
}
