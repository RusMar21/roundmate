package io.github.rusmar21.roundmate.identity.domain.account.security

import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash

interface PasswordHasher {
    suspend fun hash(password: String): PasswordHash
}
