package io.github.rusmar21.roundmate.system.api.grpc

import io.github.rusmar21.roundmate.annotations.GrpcIntegrationTest
import io.github.rusmar21.system.api.grpc.v1.PingRequest
import io.github.rusmar21.system.api.grpc.v1.SystemServiceGrpc
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

@GrpcIntegrationTest(
    types = [SystemServiceGrpc.SystemServiceBlockingStub::class],
)
class SystemServiceGrpcTest(
    private val systemServiceGrpcStub: SystemServiceGrpc.SystemServiceBlockingStub,
) {
    @Test
    fun `ping test`() {
        val request = PingRequest.getDefaultInstance()
        val response = systemServiceGrpcStub.ping(request)

        assertThat(response.message).isEqualTo("pong")
    }
}
