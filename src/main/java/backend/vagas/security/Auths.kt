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
        var expiresIn: Instant? = null
    }

    data object UserData {
        var id: UUID? = null
        var userName: String? = null
        var password: String? = null
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
        AuthResponse.expiresIn = Instant.now().plus(ofMinutes(10))
        AuthResponse.acessToken = JWT.create()
            .withIssuer("Vagas")
            .withClaim("roles", listOf("candidate"))
            .withExpiresAt(AuthResponse.expiresIn)
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
                    }
                }.onFailure {
                    throw UsernameNotFoundException("Username or Password incorrect")
                }
        }
        return UserData
    }

}