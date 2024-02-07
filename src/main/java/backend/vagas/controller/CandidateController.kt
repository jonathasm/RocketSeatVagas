package backend.vagas.controller

import backend.vagas.model.Candidate
import backend.vagas.service.CandidateService
import backend.vagas.service.dto.CandidateDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/candidate")
class CandidateController(var candidateService: CandidateService) {
    @PostMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    fun create(@RequestBody @Valid candidate: Candidate): ResponseEntity<CandidateDto> {
        return candidateService.createCandidate(candidate)
    }


}
