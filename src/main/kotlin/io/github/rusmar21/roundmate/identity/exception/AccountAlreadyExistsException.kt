package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class AccountAlreadyExistsException :
    IdentityException(
        status = Status.Code.INVALID_ARGUMENT,
        errorCode = IdentityErrorCode.ACCOUNT_ALREADY_EXISTS,
        message = "Account already exists",
    )
