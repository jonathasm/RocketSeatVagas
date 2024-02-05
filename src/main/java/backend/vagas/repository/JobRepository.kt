package backend.vagas.repository

import backend.vagas.model.Job
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JobRepository : JpaRepository<Job, UUID>