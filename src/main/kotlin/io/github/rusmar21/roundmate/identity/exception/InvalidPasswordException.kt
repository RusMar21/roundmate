package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class InvalidPasswordException :
    IdentityException(
        status = Status.Code.INVALID_ARGUMENT,
        errorCode = IdentityErrorCode.INVALID_PASSWORD_LENGTH,
        message = "Invalid password length",
    )
