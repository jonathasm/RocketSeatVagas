package backend.vagas.controller

import backend.vagas.model.Job
import backend.vagas.service.JobService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/job")
class JobController(val jobService: JobService) {
    fun create(@Valid @RequestBody job: Job): ResponseEntity<Job> {
        return this.jobService.createJob(job)
    }

}