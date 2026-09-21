package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class InvalidEmailLengthException :
    IdentityException(
        status = Status.Code.INVALID_ARGUMENT,
        errorCode = IdentityErrorCode.INVALID_EMAIL_LENGTH,
        message = "Invalid email length",
    )
