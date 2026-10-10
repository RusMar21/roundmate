package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class InvalidFieldFormatException :
    IdentityException(
        status = Status.Code.INVALID_ARGUMENT,
        errorCode = IdentityErrorCode.INVALID_FIELD_FORMAT,
        message = "Invalid field format",
    )
