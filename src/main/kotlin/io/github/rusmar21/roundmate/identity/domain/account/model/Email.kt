package io.github.rusmar21.roundmate.identity.domain.account.model

import io.github.rusmar21.roundmate.identity.exception.InvalidEmailFormatException
import io.github.rusmar21.roundmate.identity.exception.InvalidEmailLengthException

private const val MAX_EMAIL_LENGTH = 254
private val EMAIL_PATTERN = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")

@JvmInline
value class Email(
    val email: String,
) {
    companion object {
        fun from(emailString: String): Email {
            val normalizeEmail = emailString.trim().lowercase()
            require(normalizeEmail.length <= MAX_EMAIL_LENGTH) { throw InvalidEmailLengthException() }
            require(EMAIL_PATTERN.matches(normalizeEmail)) { throw InvalidEmailFormatException() }
            return Email(normalizeEmail)
        }
    }
}
