package backend.vagas.service

import backend.vagas.model.Job
import backend.vagas.repository.JobRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class JobService(val jobRepository: JobRepository) {
    fun createJob(job: Job): ResponseEntity<Job> {
        val createJobOrFail = runCatching { jobRepository.save(job) }
            .onFailure {
                throw Exception(it.message)
            }
        return ResponseEntity.ok(createJobOrFail.getOrThrow())
    }
}
