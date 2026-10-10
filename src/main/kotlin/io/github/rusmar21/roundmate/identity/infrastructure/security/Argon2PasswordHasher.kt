package io.github.rusmar21.roundmate.identity.infrastructure.security

import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash
import io.github.rusmar21.roundmate.identity.domain.account.security.PasswordHasher
import org.springframework.stereotype.Component

@Component
class Argon2PasswordHasher : PasswordHasher {
    override suspend fun hash(password: String): PasswordHash = PasswordHash(password)
}
