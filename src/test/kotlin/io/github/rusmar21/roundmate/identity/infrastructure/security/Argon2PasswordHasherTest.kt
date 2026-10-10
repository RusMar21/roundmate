package io.github.rusmar21.roundmate.identity.infrastructure.security

import io.github.rusmar21.roundmate.annotations.LocalSpringContextTest
import io.github.rusmar21.roundmate.identity.domain.account.security.PasswordHasher
import io.github.rusmar21.roundmate.identity.infrastructure.security.configuration.PasswordSecurityConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@LocalSpringContextTest(
    classes = [
        PasswordSecurityConfiguration::class,
        Argon2PasswordHasher::class,
    ],
)
class Argon2PasswordHasherTest(
    private val passwordHasher: PasswordHasher,
) {
    @Test
    fun `hashes password using argon2id`() {
        val passwordHash = passwordHasher.hash(PASSWORD)

        assertThat(passwordHash.hash)
            .startsWith("\$argon2id\$")

        assertThat(passwordHash.hash)
            .isNotEqualTo(PASSWORD)
    }

    @Test
    fun `matches original password`() {
        val passwordHash = passwordHasher.hash(PASSWORD)

        val result =
            passwordHasher.matches(
                password = PASSWORD,
                passwordHash = passwordHash,
            )

        assertThat(result).isTrue()
    }

    @Test
    fun `does not match different password`() {
        val passwordHash = passwordHasher.hash(PASSWORD)

        val result =
            passwordHasher.matches(
                password = "different-password",
                passwordHash = passwordHash,
            )

        assertThat(result).isFalse()
    }

    @Test
    fun `uses different salt for each password hash`() {
        val firstHash = passwordHasher.hash(PASSWORD)
        val secondHash = passwordHasher.hash(PASSWORD)

        assertThat(firstHash).isNotEqualTo(secondHash)
    }

    private companion object {
        const val PASSWORD = "strong-password"
    }
}
