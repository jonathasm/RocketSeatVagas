package backend.vagas.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class SecurityFilter(val jwt: Jwt = Jwt()) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader("Authorization")?.let { header ->
            runCatching {
                jwt.validateToken(header).let { it ->
                    when (it.token.isEmpty()) {
                        true -> {
                            return
                        }

                        false -> {
                            if (request.requestURI.startsWith("/company"))
                                request.setAttribute("company_id", it.subject)
                            else if (request.requestURI.startsWith("/candidate"))
                                request.setAttribute("candidate_id", it.subject)

                            val grants = listOf(it.getClaim("roles")).map {
                                SimpleGrantedAuthority(
                                    "ROLE_${
                                        it.toString()
                                            .replace("[", "")
                                            .replace("]", "")
                                            .replace("\"", "")
                                            .uppercase(Locale.getDefault())
                                    }"
                                )
                            }

                            SecurityContextHolder.getContext().authentication =
                                UsernamePasswordAuthenticationToken(it, null, grants)
                        }
                    }
                }
            }.onFailure {
                response.status = 401
                return response.writer.write("Invalid token")
            }
        }
        filterChain.doFilter(request, response)
    }
}