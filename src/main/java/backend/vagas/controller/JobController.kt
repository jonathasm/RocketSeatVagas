package backend.vagas.controller

import backend.vagas.model.Job
import backend.vagas.service.JobService
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/company/job")
class JobController(val jobService: JobService) {
    @PreAuthorize("hasRole('COMPANY')")
    fun create(@Valid @RequestBody job: Job, request: HttpServletRequest): ResponseEntity<Job> {
        request.getAttribute("company_id")?.let {
            job.companyId = it as UUID
        }
        return this.jobService.createJob(job)
    }
}