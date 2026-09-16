package io.github.rusmar21.roundmate.identity.domain.account

import io.github.rusmar21.roundmate.annotations.IntegrationTest
import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountStatus
import io.github.rusmar21.roundmate.identity.domain.account.model.RegisterAccountRequest
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import io.github.rusmar21.roundmate.identity.domain.account.security.PasswordHasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.util.UUID
import kotlin.test.assertEquals

private val ACCOUNT_UUID = UUID.randomUUID()

@IntegrationTest
class AccountServiceTest(
    private val accountService: AccountService,
    @MockitoBean
    private val accountRepository: AccountRepository,
    @MockitoBean
    private val passwordHasher: PasswordHasher,
) {
    @Test
    fun `register account successfully with hashed password`() {
        Mockito.`when`(accountRepository.findById(ACCOUNT_UUID)).thenReturn(
            Account(
                email = "example@gmail.com",
                passwordHash = "hashPassword",
                name = "Test",
                surname = "Test",
                accountId = ACCOUNT_UUID,
                status = AccountStatus.ACTIVE,
            ),
        )

        val registerAccountRequest =
            RegisterAccountRequest(
                email = "example@gmail.com",
                password = "password",
                name = "Test",
                surname = "Test",
            )
        val result = accountService.registerAccount(registerAccountRequest)

        val user = accountRepository.findById(result)

        assertThat(user).isNotNull
        assertEquals("example@gmail.com", user?.email)
        assertEquals("Test", user?.name)
        assertEquals("Test", user?.surname)
        assertEquals(AccountStatus.ACTIVE, user?.status)
        assertEquals("hashPassword", user?.passwordHash)
    }
}
