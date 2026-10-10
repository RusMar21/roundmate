package io.github.rusmar21.roundmate.identity.infrastructure.security

import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash
import io.github.rusmar21.roundmate.identity.domain.account.security.PasswordHasher
import io.github.rusmar21.roundmate.identity.exception.PasswordEncodingException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Argon2PasswordHasher(
    private val passwordEncoder: PasswordEncoder,
) : PasswordHasher {
    override fun hash(password: String): PasswordHash {
        val encodedPassword = passwordEncoder.encode(password) ?: throw PasswordEncodingException()
        return PasswordHash(encodedPassword)
    }

    override fun matches(
        password: String,
        passwordHash: PasswordHash,
    ): Boolean =
        passwordEncoder.matches(
            password,
            passwordHash.hash,
        )
}
