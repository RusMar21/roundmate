package io.github.rusmar21.roundmate.identity.domain.account.security

import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash

interface PasswordHasher {
    fun hash(password: String): PasswordHash

    fun matches(
        password: String,
        passwordHash: PasswordHash,
    ): Boolean
}
