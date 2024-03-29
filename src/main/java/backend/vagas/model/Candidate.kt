package backend.vagas.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import java.util.*

@Data
@Entity(name = "candidate")
data class Candidate(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var name: String? = null,
    var username: @Pattern(
        regexp = "^[a-zA-Z0-9]*$",
        message = "Username should contain only letters and numbers"
    ) String? = null,
    var email: @Email(message = "Email should be valid") String? = null,

    var password: @Length(min = 6, message = "Password should have at least 6 characters") String? = null,
    var description: String? = null,
    var curriculum: String? = null,

    @CreationTimestamp
    private val createdAt: LocalDateTime? = null
)
