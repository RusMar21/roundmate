package io.github.rusmar21.roundmate.configuration

import io.grpc.BindableService
import io.grpc.ServerServiceDefinition
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration(proxyBeanMethods = false)
class GrpcTestConfiguration {
    @Bean
    fun grpcTestService(): BindableService =
        BindableService {
            ServerServiceDefinition
                .builder("roundmate.test.BootstrapService")
                .build()
        }
}
