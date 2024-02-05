package backend.vagas.service

import backend.vagas.controller.exception.UserNotFoundException
import backend.vagas.model.Candidate
import backend.vagas.repository.CandidateRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class CandidateService(private val candidateRepository: CandidateRepository) {
    fun createCandidate(candidate: Candidate): ResponseEntity<Candidate> {

        val candidateOrFail: ((candidate: Candidate) -> Optional<Candidate?>?)? = {
            candidateRepository.findByUsernameOrEmail(it.username, it.email)
        }
        runCatching { candidateOrFail?.invoke(candidate) }
            .onFailure {
                throw UserNotFoundException()
            }
        return ResponseEntity.ok(candidateRepository.save(candidate))
    }
}
