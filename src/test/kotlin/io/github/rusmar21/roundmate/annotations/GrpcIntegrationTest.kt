package io.github.rusmar21.roundmate.annotations

import io.github.rusmar21.roundmate.TestcontainersConfiguration
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureTestGrpcTransport
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.grpc.client.ImportGrpcClients
import org.springframework.test.context.TestConstructor
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@AutoConfigureTestGrpcTransport
@Import(TestcontainersConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ImportGrpcClients
annotation class GrpcIntegrationTest(
    @get:AliasFor(
        annotation = ImportGrpcClients::class,
        attribute = "types",
    )
    val types: Array<KClass<*>> = [],
)
