package backend.vagas.security

import backend.vagas.repository.CompanyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthCompany {

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Throws(AuthenticationException::class)
    fun authExecute(authCompanyDto: AuthCompanyDto) {
        authCompanyDto.username
            ?.let { companyRepository.findByUsername(it) }
            ?.orElseThrow { throw UsernameNotFoundException("Company not found") }
            ?.let { company ->
                runCatching {
                    check(passwordEncoder.matches(authCompanyDto.password, company.password))
                }.onSuccess {
                    

                }.onFailure {
                    throw UsernameNotFoundException("Invalid password")
                }

            }

    }

}
