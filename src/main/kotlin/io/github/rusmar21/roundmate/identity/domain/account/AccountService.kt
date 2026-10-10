package io.github.rusmar21.roundmate.identity.domain.account

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.Email
import io.github.rusmar21.roundmate.identity.domain.account.model.RegisterAccountRequest
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import io.github.rusmar21.roundmate.identity.domain.account.security.PasswordHasher
import io.github.rusmar21.roundmate.identity.domain.account.security.PasswordValidator
import io.github.rusmar21.roundmate.identity.exception.AccountAlreadyExistsException
import io.github.rusmar21.roundmate.identity.util.normalizeAndValidate
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordHasher: PasswordHasher,
    private val passwordValidator: PasswordValidator,
) {
    suspend fun registerAccount(registerAccountRequest: RegisterAccountRequest): AccountId {
        val email = Email.from(registerAccountRequest.email)
        checkEmailIsAvailable(email)
        val normalizeName = registerAccountRequest.name.normalizeAndValidate()
        val normalizeSurname = registerAccountRequest.surname.normalizeAndValidate()
        passwordValidator.validatePassword(registerAccountRequest.password)
        val passwordHash = passwordHasher.hash(registerAccountRequest.password)
        val account =
            Account(
                email = email,
                passwordHash = passwordHash,
                name = normalizeName,
                surname = normalizeSurname,
            )
        val accountId = accountRepository.save(account)
        log.info { "Account registered successfully: $accountId" }
        return accountId
    }

    private suspend fun checkEmailIsAvailable(email: Email) =
        require(accountRepository.findByEmail(email) == null) {
            throw AccountAlreadyExistsException()
        }
}
