package io.github.rusmar21.roundmate.integration.grpc

import io.github.rusmar21.roundmate.annotations.GrpcIntegrationTest
import io.grpc.BindableService
import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.health.v1.HealthGrpc
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import org.springframework.grpc.server.GrpcServerFactory

@GrpcIntegrationTest(
    types = [HealthGrpc.HealthBlockingStub::class],
)
class GrpcHealthTest(
    private val stub: HealthGrpc.HealthBlockingStub,
    private val applicationContext: ApplicationContext,
) {
    @Test
    fun `Health Check OK`() {
        println(
            applicationContext
                .getBeansOfType(BindableService::class.java)
                .keys,
        )

        println(
            applicationContext
                .getBeansOfType(GrpcServerFactory::class.java)
                .keys,
        )

        val response =
            stub.check(
                HealthCheckRequest.getDefaultInstance(),
            )
        assertThat(response.status)
            .isEqualTo(HealthCheckResponse.ServingStatus.SERVING)
    }
}
