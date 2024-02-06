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
    companion object UserData {
        var id: UUID? = null
        var userName: String? = null
        var password: String? = null
    }

    fun authCompany(authsDto: AuthsDto): String {
        findUsername(companyRepository, authsDto).let {
            return executeAuth(it, authsDto)
        }
    }

    fun authCandidate(authsDto: AuthsDto): String {
        findUsername(candidateRepository, authsDto).let {
            return executeAuth(it, authsDto)
        }
    }

    fun executeAuth(userData: UserData, authsDto: AuthsDto): String {
        BCryptPasswordEncoder().matches(authsDto.password, userData.password).let {
            if (!it) throw UsernameNotFoundException("Username or Password incorrect")
        }
        return JWT.create()
            .withIssuer("Vagas")
            .withClaim("roles", listOf("candidate"))
            .withExpiresAt(Instant.now().plus(ofMinutes(10)))
            .withSubject(userData.id.toString())
            .sign(HMAC256("secret"))
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