package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class PasswordEncodingException :
    IdentityException(
        status = Status.Code.INTERNAL,
        errorCode = IdentityErrorCode.INVALID_PASSWORD_ENCODING,
        message = "Invalid password encoding",
    )
