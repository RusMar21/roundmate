package io.github.rusmar21.roundmate.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringJUnitConfig
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
annotation class LocalSpringContextTest(
    @get:AliasFor(
        annotation = SpringJUnitConfig::class,
        attribute = "classes",
    )
    val classes: Array<KClass<*>> = [],
)
