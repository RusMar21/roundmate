package io.github.rusmar21.roundmate.identity.domain.account.model

data class Account(
    val accountId: AccountId,
    val email: Email,
    val name: String,
    val surname: String,
    val passwordHash: PasswordHash,
    val status: AccountStatus = AccountStatus.ACTIVE,
)
