package backend.vagas.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity(name = "job")
data class Job(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var description: String? = null,
    var benefits: String? = null,

    @NotBlank(message = "Esse campo é obrigatório")
    var level: String? = null,

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    var company: Company? = null,

    @Column(name = "company_id", nullable = false)

    var companyId: UUID? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null
)
