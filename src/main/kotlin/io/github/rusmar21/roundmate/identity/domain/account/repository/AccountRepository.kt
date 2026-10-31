package io.github.rusmar21.roundmate.identity.domain.account.repository

import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.Email

interface AccountRepository {
    fun findByEmail(email: Email): Account?

    fun findById(id: AccountId): Account?

    fun save(account: Account): AccountId
}
