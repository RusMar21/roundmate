package io.github.RusMar21.roundmate.integration.database

import io.github.RusMar21.roundmate.annotations.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate

@IntegrationTest
class LiquibaseSchemaIntegrationName(
    private val jdbcTemplate: JdbcTemplate,
) {

    @Test
    fun `check schemas exists in db`() {
        val schemas = jdbcTemplate.queryForList(
            """
                SELECT schema_name 
                FROM information_schema.schemata
            """.trimIndent(),
            String::class.java
        )

        assertThat(schemas).contains(
            "identity",
            "club",
            "training"
        )
    }
}