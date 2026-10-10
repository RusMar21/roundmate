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
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.springframework.test.context.bean.override.mockito.MockitoBean

@IntegrationTest
class AccountServiceTest(
    private val accountService: AccountService,
    @MockitoBean
    private val accountRepository: AccountRepository,
) {
    @Test
    fun `registers account and returns generated account id`() =
        runTest {
            val generatedId = AccountId(42)
            Mockito
                .`when`(accountRepository.findByEmail(any()))
                .thenReturn(null)
            Mockito
                .`when`(accountRepository.save(any()))
                .thenReturn(generatedId)

            val result =
                accountService.registerAccount(
                    request(
                        email = "coach@example.com",
                        password = "strong-password",
                    ),
                )

            assertThat(result).isEqualTo(generatedId)

            val accountCaptor = argumentCaptor<Account>()
            verify(accountRepository).save(accountCaptor.capture())

            val savedAccount = accountCaptor.firstValue
            assertThat(savedAccount.accountId).isNull()
            assertThat(savedAccount.email.email).isEqualTo("coach@example.com")
            assertThat(savedAccount.passwordHash.hash).isNotEmpty()
            assertThat(savedAccount.name).isEqualTo("Ivan")
            assertThat(savedAccount.surname).isEqualTo("Ivanov")
            assertThat(savedAccount.status).isEqualTo(AccountStatus.ACTIVE)
        }

    @Test
    fun `normalizes email before checking and saving account`() =
        runTest {
            val generatedId = AccountId(42)

            Mockito
                .`when`(accountRepository.findByEmail(any()))
                .thenReturn(null)
            Mockito
                .`when`(accountRepository.save(any()))
                .thenReturn(generatedId)

            accountService.registerAccount(
                request(email = "  Coach@Example.COM  "),
            )

            verify(accountRepository).findByEmail(Email("coach@example.com"))

            val accountCaptor = argumentCaptor<Account>()
            verify(accountRepository).save(accountCaptor.capture())

            val savedEmail = accountCaptor.firstValue.email
            assertThat(savedEmail.email).isEqualTo("coach@example.com")
        }

    @Test
    fun `rejects registration when account with email already exists`() =
        runTest {
            val existedAccount =
                Account(
                    AccountId(42),
                    email = Email("coach@example.com"),
                    name = "Ivan",
                    surname = "Ivanov",
                    passwordHash = PasswordHash("argon2-hash"),
                )
            Mockito
                .`when`(accountRepository.findByEmail(Email("coach@example.com")))
                .thenReturn(existedAccount)

            val exception =
                assertThrows<AccountAlreadyExistsException> {
                    accountService.registerAccount(request())
                }

            assertThat(exception.errorCode)
                .isEqualTo(IdentityErrorCode.ACCOUNT_ALREADY_EXISTS)

            verify(accountRepository, never()).save(any())
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
    fun `rejects invalid email`(email: String) =
        runTest {
            val exception =
                assertThrows<InvalidEmailFormatException> {
                    accountService.registerAccount(request(email = email))
                }

            assertThat(exception.errorCode)
                .isEqualTo(IdentityErrorCode.INVALID_EMAIL_FORMAT)
        }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `rejects blank name`(name: String) =
        runTest {
            val exception =
                assertThrows<InvalidFieldFormatException> {
                    accountService.registerAccount(request(name = name))
                }

            assertThat(exception.errorCode)
                .isEqualTo(IdentityErrorCode.INVALID_FIELD_FORMAT)
        }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `rejects blank surname`(surname: String) =
        runTest {
            val exception =
                assertThrows<InvalidFieldFormatException> {
                    accountService.registerAccount(request(surname = surname))
                }

            assertThat(exception.errorCode)
                .isEqualTo(IdentityErrorCode.INVALID_FIELD_FORMAT)
        }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   "])
    fun `rejects blank password`(password: String) =
        runTest {
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
