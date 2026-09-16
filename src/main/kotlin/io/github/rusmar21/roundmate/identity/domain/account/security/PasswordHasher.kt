package io.github.rusmar21.roundmate.identity.domain.account.security

import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash

interface PasswordHasher {
    fun hashPassword(password: String): PasswordHash
}
