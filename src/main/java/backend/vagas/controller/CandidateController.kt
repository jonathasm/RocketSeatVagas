package backend.vagas.controller

import backend.vagas.model.Candidate
import backend.vagas.service.CandidateService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/candidate")
class CandidateController(var candidateService: CandidateService) {
    @PostMapping
    fun create(@RequestBody candidate: @Valid Candidate?): ResponseEntity<Candidate> {
        return candidateService.createCandidate(candidate!!)
    }
}
