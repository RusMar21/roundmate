package io.github.rusmar21.roundmate.identity.domain.account.security

import io.github.rusmar21.roundmate.identity.exception.InvalidPasswordException
import org.springframework.stereotype.Component

private const val PASSWORD_MIN_LENGTH = 8

@Component
class PasswordValidator {
    suspend fun validatePassword(password: String) {
        val normalizePassword = password.trim()
        require(normalizePassword.length >= PASSWORD_MIN_LENGTH) {
            throw InvalidPasswordException()
        }
    }
}
