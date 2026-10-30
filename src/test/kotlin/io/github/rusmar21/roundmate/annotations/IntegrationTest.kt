package io.github.rusmar21.roundmate.annotations

import io.github.rusmar21.roundmate.configuration.TestcontainersConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.jdbc.Sql

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@Import(TestcontainersConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql(
    scripts = ["classpath:sql/truncate_tables.sql"],
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
)
annotation class IntegrationTest
