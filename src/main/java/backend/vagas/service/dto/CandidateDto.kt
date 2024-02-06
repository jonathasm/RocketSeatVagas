package backend.vagas.service.dto

import java.util.*

data class CandidateDto(
    var id: UUID? = null,
    var name: String? = null,
    var username: String? = null,
    var email: String? = null
)