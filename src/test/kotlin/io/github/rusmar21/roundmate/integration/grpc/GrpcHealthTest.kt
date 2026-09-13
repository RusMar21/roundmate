package io.github.rusmar21.roundmate.integration.grpc

import io.github.rusmar21.roundmate.annotations.GrpcIntegrationTest
import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.health.v1.HealthGrpc
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@GrpcIntegrationTest(
    types = [HealthGrpc.HealthBlockingStub::class],
)
class GrpcHealthTest(
    private val stub: HealthGrpc.HealthBlockingStub,
) {
    @Test
    fun `Health Check OK`() {
        val response =
            stub.check(
                HealthCheckRequest.getDefaultInstance(),
            )
        assertThat(response.status)
            .isEqualTo(HealthCheckResponse.ServingStatus.SERVING)
    }
}
