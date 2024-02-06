package backend.vagas.security

import backend.vagas.repository.CandidateRepository
import backend.vagas.repository.CompanyRepository
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.Duration.ofMinutes
import java.time.Instant
import java.util.*

@Service
class Auths(val companyRepository: CompanyRepository, val candidateRepository: CandidateRepository) {
    companion object AuthResponse {
        var acessToken: String? = null
        var expiresIn: String? = null
    }

    data object UserData {
        var id: UUID? = null
        var userName: String? = null
        var password: String? = null
        var roles: List<String>? = null
    }


    fun authCompany(authsDto: AuthsDto): AuthResponse {
        findUsername(companyRepository, authsDto).let {
            return executeAuth(it, authsDto)
        }
    }

    fun authCandidate(authsDto: AuthsDto): AuthResponse {
        findUsername(candidateRepository, authsDto).let {
            return executeAuth(it, authsDto)
        }
    }

    fun executeAuth(userData: UserData, authsDto: AuthsDto): AuthResponse {
        BCryptPasswordEncoder().matches(authsDto.password, userData.password).let {
            if (!it) throw UsernameNotFoundException("Username or Password incorrect")
        }
        val expiresAt = Instant.now().plus(ofMinutes(10))
        AuthResponse.expiresIn = expiresAt.toString().substringBefore(".").replace("T", " ")
        AuthResponse.acessToken = JWT.create()
            .withIssuer("Vagas")
            .withClaim("roles", userData.roles)
            .withExpiresAt(expiresAt)
            .withSubject(userData.id.toString())
            .sign(HMAC256("secret"))
        return AuthResponse
    }


    fun findUsername(repository: Any, authsDto: AuthsDto): UserData {
        when (repository) {
            is CompanyRepository ->
                runCatching {
                    repository.findByUsername(authsDto.username).let {
                        UserData.id = it?.id
                        UserData.userName = it?.username
                        UserData.password = it?.password
                        UserData.roles = listOf("company")
                    }
                }.onFailure {
                    throw UsernameNotFoundException("Username or Password incorrect")
                }

            is CandidateRepository ->
                runCatching {
                    repository.findByUsername(authsDto.username).let {
                        UserData.id = it?.id
                        UserData.userName = it?.username
                        UserData.password = it?.password
                        UserData.roles = listOf("candidate")
                    }
                }.onFailure {
                    throw UsernameNotFoundException("Username or Password incorrect")
                }
        }
        return UserData
    }

}