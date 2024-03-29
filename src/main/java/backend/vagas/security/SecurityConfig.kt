package backend.vagas.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(val securityFilter: SecurityFilter) {

    val swagger = arrayOf(
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-resource/**",
    )

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf(Customizer { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() })
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests(
                Customizer { auth ->
                    auth.requestMatchers("/auth/**").permitAll()
                    auth.requestMatchers("/company").hasRole("COMPANY")
                    auth.requestMatchers("/candidate").hasRole("CANDIDATE")
                        .requestMatchers(*swagger).permitAll()
                        .anyRequest().authenticated()
                }).addFilterBefore(securityFilter, BasicAuthenticationFilter::class.java)

        return http.build()
    }
}