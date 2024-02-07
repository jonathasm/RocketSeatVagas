package backend.vagas.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Service


@Service
class Jwt {
    fun validateToken(token: String): DecodedJWT = runCatching {
        JWT.require(
            HMAC256("secretKey")
        )
            .build()
            .verify(
                token.replace("Bearer ", "")
            )
    }.onFailure {
        throw JWTVerificationException("Invalid token")
    }.getOrThrow()
}