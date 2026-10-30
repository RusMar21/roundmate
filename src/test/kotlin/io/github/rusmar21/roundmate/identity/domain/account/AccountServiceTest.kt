package io.github.rusmar21.roundmate.identity.domain.account

import io.github.rusmar21.roundmate.annotations.IntegrationTest
import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountStatus
import io.github.rusmar21.roundmate.identity.domain.account.model.Email
import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash
import io.github.rusmar21.roundmate.identity.domain.account.model.RegisterAccountRequest
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import io.github.rusmar21.roundmate.identity.exception.AccountAlreadyExistsException
import io.github.rusmar21.roundmate.identity.exception.IdentityErrorCode
import io.github.rusmar21.roundmate.identity.exception.InvalidEmailFormatException
import io.github.rusmar21.roundmate.identity.exception.InvalidFieldFormatException
import io.github.rusmar21.roundmate.identity.exception.InvalidPasswordException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@IntegrationTest
class AccountServiceTest(
    private val accountService: AccountService,
    private val accountRepository: AccountRepository,
) {
    @Test
    fun `registers account and returns generated account id`() {
        accountService.registerAccount(
            request(
                email = "coach@example.com",
                password = "strong-password",
            ),
        )

        val savedAccount = accountRepository.findByEmail(Email("coach@example.com"))
        assertThat(savedAccount?.email?.value).isEqualTo("coach@example.com")
        assertThat(savedAccount?.passwordHash?.hash).isNotEmpty()
        assertThat(savedAccount?.name).isEqualTo("Ivan")
        assertThat(savedAccount?.surname).isEqualTo("Ivanov")
        assertThat(savedAccount?.status).isEqualTo(AccountStatus.ACTIVE)
    }

    @Test
    fun `normalizes email before checking and saving account`() {
        accountService.registerAccount(
            request(email = "  Coach@Example.COM  "),
        )

        assertNotNull(accountRepository.findByEmail(Email("coach@example.com")))
    }

    @Test
    fun `rejects registration when account with email already exists`() {
        val existedAccount =
            Account(
                AccountId(42),
                email = Email("coach@example.com"),
                name = "Ivan",
                surname = "Ivanov",
                passwordHash = PasswordHash("argon2-hash"),
            )
        accountRepository.save(existedAccount)
        val exception =
            assertThrows<AccountAlreadyExistsException> {
                accountService.registerAccount(request())
            }

        assertThat(exception.errorCode)
            .isEqualTo(IdentityErrorCode.ACCOUNT_ALREADY_EXISTS)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            " ",
            "invalid-email",
            "@example.com",
            "coach@",
        ],
    )
    fun `rejects invalid email`(email: String) {
        val exception =
            assertThrows<InvalidEmailFormatException> {
                accountService.registerAccount(request(email = email))
            }

        assertThat(exception.errorCode)
            .isEqualTo(IdentityErrorCode.INVALID_EMAIL_FORMAT)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `rejects blank name`(name: String) {
        val exception =
            assertThrows<InvalidFieldFormatException> {
                accountService.registerAccount(request(name = name))
            }

        assertThat(exception.errorCode)
            .isEqualTo(IdentityErrorCode.INVALID_FIELD_FORMAT)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `rejects blank surname`(surname: String) {
        val exception =
            assertThrows<InvalidFieldFormatException> {
                accountService.registerAccount(request(surname = surname))
            }

        assertThat(exception.errorCode)
            .isEqualTo(IdentityErrorCode.INVALID_FIELD_FORMAT)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `rejects blank password`(password: String) {
        val exception =
            assertThrows<InvalidPasswordException> {
                accountService.registerAccount(request(password = password))
            }

        assertThat(exception.errorCode)
            .isEqualTo(IdentityErrorCode.INVALID_PASSWORD_LENGTH)
    }

    private fun request(
        email: String = "coach@example.com",
        password: String = "strong-password",
        name: String = "Ivan",
        surname: String = "Ivanov",
    ) = RegisterAccountRequest(
        email = email,
        password = password,
        name = name,
        surname = surname,
    )
}
