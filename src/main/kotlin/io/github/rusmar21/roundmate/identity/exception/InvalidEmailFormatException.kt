package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class InvalidEmailFormatException :
    IdentityException(
        status = Status.Code.INVALID_ARGUMENT,
        errorCode = IdentityErrorCode.INVALID_EMAIL_FORMAT,
        message = "Invalid email format",
    )
