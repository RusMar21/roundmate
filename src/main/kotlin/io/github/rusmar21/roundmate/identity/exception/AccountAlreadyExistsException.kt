package io.github.rusmar21.roundmate.identity.exception

import io.grpc.Status

class AccountAlreadyExistsException :
    IdentityException(
        status = Status.Code.ALREADY_EXISTS,
        errorCode = IdentityErrorCode.ACCOUNT_ALREADY_EXISTS,
        message = "Account already exists",
    )
