package io.github.rusmar21.roundmate.identity

import io.github.rusmar21.roundmate.identity.domain.account.AccountService
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.RegisterAccountRequest
import org.springframework.stereotype.Component

@Component
class IdentityFacade(
    private val accountService: AccountService,
) {
    suspend fun registerAccount(request: RegisterAccountRequest): AccountId = accountService.registerAccount(request)
}
