package io.github.rusmar21.roundmate.identity.domain.account.model

data class RegisterAccountRequest(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
)
