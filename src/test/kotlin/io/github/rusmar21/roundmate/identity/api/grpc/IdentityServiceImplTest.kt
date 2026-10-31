package io.github.rusmar21.roundmate.identity.api.grpc

import io.github.rusmar21.roundmate.annotations.GrpcIntegrationTest
import io.github.rusmar21.roundmate.identity.api.grpc.generated.IdentityServiceGrpcKt
import io.github.rusmar21.roundmate.identity.api.grpc.generated.RegisterRequest
import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountStatus
import io.github.rusmar21.roundmate.identity.domain.account.model.Email
import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import io.github.rusmar21.roundmate.identity.exception.IdentityErrorCode
import io.grpc.Status
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@GrpcIntegrationTest(
    types = [
        IdentityServiceGrpcKt.IdentityServiceCoroutineStub::class,
    ],
)
class IdentityServiceImplTest(
    private val identityServiceStub: IdentityServiceGrpcKt.IdentityServiceCoroutineStub,
    private val accountRepository: AccountRepository,
) {
    @Test
    fun `should create account`() =
        runTest {
            val request = request()

            val response = identityServiceStub.register(request)
            val accountId = AccountId(response.accountId)

            val account = accountRepository.findById(accountId)

            assertThat(account).isNotNull
            assertThat(account?.accountId).isEqualTo(accountId)
            assertThat(account?.email?.value).isEqualTo(request.email)
            assertThat(account?.name).isEqualTo(request.name)
            assertThat(account?.surname).isEqualTo(request.surname)
        }

    @Test
    fun `should return invalid argument when email is invalid`() =
        runTest {
            val request = request(email = "invalid_email")
            val exception =
                runCatching {
                    identityServiceStub.register(request)
                }.exceptionOrNull()
            val status = Status.fromThrowable(exception)

            assertThat(status.code).isEqualTo(Status.Code.INVALID_ARGUMENT)
            assertThat(status.description).contains(IdentityErrorCode.INVALID_EMAIL_FORMAT.name)
        }

    @Test
    fun `should return already exists when email is already registered`() =
        runTest {
            accountRepository.save(account())
            val request = request(email = DEFAULT_EMAIL)
            val exception =
                runCatching {
                    identityServiceStub.register(request)
                }.exceptionOrNull()
            val status = Status.fromThrowable(exception)

            assertThat(status.code).isEqualTo(Status.Code.ALREADY_EXISTS)
            assertThat(status.description).contains(IdentityErrorCode.ACCOUNT_ALREADY_EXISTS.name)
        }

    private fun request(
        email: String = DEFAULT_EMAIL,
        password: String = DEFAULT_PASSWORD,
        name: String = DEFAULT_NAME,
        surname: String = DEFAULT_SURNAME,
    ): RegisterRequest =
        RegisterRequest
            .newBuilder()
            .setEmail(email)
            .setPassword(password)
            .setName(name)
            .setSurname(surname)
            .build()

    private fun account(
        email: String = DEFAULT_EMAIL,
        passwordHash: String = DEFAULT_HASH,
        name: String = DEFAULT_NAME,
        surname: String = DEFAULT_SURNAME,
    ): Account =
        Account(
            email = Email(email),
            name = name,
            surname = surname,
            passwordHash = PasswordHash(passwordHash),
            status = AccountStatus.ACTIVE,
        )

    companion object {
        private const val DEFAULT_EMAIL = "user@mail.com"
        private const val DEFAULT_PASSWORD = "password"
        private const val DEFAULT_NAME = "user"
        private const val DEFAULT_SURNAME = "surname"
        private const val DEFAULT_HASH = "hash"
    }
}
