package io.github.rusmar21.roundmate.identity.domain.account.repository

import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository {
    fun findById(accountID: AccountId): Account?
}
