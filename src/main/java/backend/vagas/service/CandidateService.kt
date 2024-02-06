package backend.vagas.service

import backend.vagas.controller.exception.UserException
import backend.vagas.model.Candidate
import backend.vagas.repository.CandidateRepository
import backend.vagas.service.dto.CandidateDto
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CandidateService(private val candidateRepository: CandidateRepository) {
    fun createCandidate(candidate: Candidate): ResponseEntity<CandidateDto> {
        candidateRepository.findByUsernameOrEmail(candidate.username, candidate.email)?.let {
            throw UserException()
        }
        BCryptPasswordEncoder().encode(candidate.password).let {
            candidate.password = it
        }
        val response = candidate.toCandidateDto()
        candidateRepository.save(candidate).let { response.id = it.id }
        return ResponseEntity.ok(response)
    }

    fun Candidate.toCandidateDto() = CandidateDto(
        name = name,
        username = username,
        email = email,
    )
}