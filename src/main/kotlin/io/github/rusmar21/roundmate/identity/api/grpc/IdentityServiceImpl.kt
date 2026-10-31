package io.github.rusmar21.roundmate.identity.api.grpc

import io.github.rusmar21.roundmate.identity.api.grpc.generated.IdentityServiceGrpcKt
import io.github.rusmar21.roundmate.identity.api.grpc.generated.RegisterRequest
import io.github.rusmar21.roundmate.identity.api.grpc.generated.RegisterResponse
import io.github.rusmar21.roundmate.identity.api.mapper.toDomain
import io.github.rusmar21.roundmate.identity.facade.IdentityFacade
import org.springframework.stereotype.Service

@Service
class IdentityServiceImpl(
    private val identityFacade: IdentityFacade,
) : IdentityServiceGrpcKt.IdentityServiceCoroutineImplBase() {
    override suspend fun register(request: RegisterRequest): RegisterResponse =
        RegisterResponse
            .newBuilder()
            .setAccountId(identityFacade.registerAccount(request.toDomain()).value)
            .build()
}
