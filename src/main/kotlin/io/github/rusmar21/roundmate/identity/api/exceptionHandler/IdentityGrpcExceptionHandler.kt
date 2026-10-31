package io.github.rusmar21.roundmate.identity.api.exceptionHandler

import io.github.rusmar21.roundmate.identity.exception.IdentityException
import io.grpc.Status
import org.springframework.grpc.server.advice.GrpcAdvice
import org.springframework.grpc.server.advice.GrpcExceptionHandler

@GrpcAdvice
class IdentityGrpcExceptionHandler {
    @GrpcExceptionHandler(IdentityException::class)
    fun handle(exception: IdentityException): Status =
        Status
            .fromCode(exception.status)
            .withDescription(exception.errorCode.name)
            .withCause(exception.cause)
}
