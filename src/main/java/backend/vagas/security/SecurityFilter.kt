package backend.vagas.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SecurityFilter(val jwt: Jwt = Jwt()) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        SecurityContextHolder.getContext().authentication = null

        request.getHeader("Authorization")?.let { it ->
            jwt.validateToken(it).let { subject ->
                when (subject.isEmpty()) {

                    true -> {
                        response.status = 401
                        return
                    }

                    false -> {
                        request.setAttribute("company_id", subject)
                        SecurityContextHolder.getContext().authentication =
                            UsernamePasswordAuthenticationToken(subject, null, emptyList())
                    }
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}