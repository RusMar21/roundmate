package io.github.rusmar21.roundmate.identity.api.mapper

import io.github.rusmar21.roundmate.identity.api.grpc.generated.RegisterRequest
import io.github.rusmar21.roundmate.identity.domain.account.model.RegisterAccountRequest

fun RegisterRequest.toDomain(): RegisterAccountRequest =
    RegisterAccountRequest(
        email = this.email,
        password = this.password,
        name = this.name,
        surname = this.surname,
    )
