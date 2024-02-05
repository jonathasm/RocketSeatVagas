package backend.vagas.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.validator.constraints.Length
import java.time.LocalDateTime
import java.util.*

@Entity(name = "company")
data class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @NotBlank
    @Pattern(
        regexp = "\\S+",
        message = "The [username] field must not contain spaces"
    )
    var username: String? = null,

    @Email(message = "The [email] field must contain a valid email address")
    var email: String? = null,

    @Length(
        min = 10,
        max = 100,
        message = "The password must contain between (10) and (100) characters"
    )
    var password: String? = null,

    var website: String? = null,
    var name: String? = null,
    var description: String? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null
)
