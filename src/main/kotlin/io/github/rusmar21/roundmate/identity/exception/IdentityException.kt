package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

open class IdentityException(
    val status: Status.Code,
    val errorCode: IdentityErrorCode,
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
