package io.github.rusmar21.roundmate.identity.infrastructure.jdbc

import io.github.rusmar21.roundmate.annotations.IntegrationTest
import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.Email
import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataIntegrityViolationException

@IntegrationTest
class JdbcAccountRepositoryTest(
    private val accountRepository: AccountRepository,
) {
    @Test
    fun `should save and found account by email`() {
        val accountId = accountRepository.save(ACCOUNT)
        val savedAccount = accountRepository.findByEmail(ACCOUNT.email)

        assertThat(savedAccount?.accountId?.value).isEqualTo(accountId.value)
    }

    @Test
    fun `should return null when account doesn't exist`() {
        val result = accountRepository.findByEmail(Email("test@test.com"))

        assertThat(result).isNull()
    }

    @Test
    fun `should reject duplicate email when account already exists`() {
        accountRepository.save(ACCOUNT)

        assertThrows<DataIntegrityViolationException> {
            accountRepository.save(ACCOUNT)
        }
    }

    companion object {
        val ACCOUNT =
            Account(
                email = Email("coach@example.com"),
                name = "Ivan",
                surname = "Ivanov",
                passwordHash = PasswordHash("argon2-hash"),
            )
    }
}
