package backend.vagas.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.stereotype.Service


@Service
class Jwt {
    fun validateToken(token: String): DecodedJWT {
        return JWT
            .require(HMAC256("secret"))
            .build()
            .verify(
                token.replace("Bearer ", "")
            )
    }
}