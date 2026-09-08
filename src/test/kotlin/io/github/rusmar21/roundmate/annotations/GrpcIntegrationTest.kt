package io.github.rusmar21.roundmate.annotations

import io.github.rusmar21.roundmate.configuration.GrpcTestConfiguration
import io.github.rusmar21.roundmate.configuration.TestcontainersConfiguration
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureTestGrpcTransport
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.grpc.client.ImportGrpcClients
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@AutoConfigureTestGrpcTransport
@Import(TestcontainersConfiguration::class, GrpcTestConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ImportGrpcClients
@TestPropertySource(
    properties = [
        "spring.grpc.server.health.enabled=true",
    ],
)
annotation class GrpcIntegrationTest(
    @get:AliasFor(
        annotation = ImportGrpcClients::class,
        attribute = "types",
    )
    val types: Array<KClass<*>> = [],
)
