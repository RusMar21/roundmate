package io.github.rusmar21.roundmate.integration.database

import io.github.rusmar21.roundmate.annotations.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate

@IntegrationTest
class LiquibaseMigrationTest(
    private val jdbcTemplate: JdbcTemplate,
) {
    @Test
    fun `check schemas exists in db`() {
        val schemas =
            jdbcTemplate.queryForList(
                """
                SELECT schema_name 
                FROM information_schema.schemata
                """.trimIndent(),
                String::class.java,
            )

        assertThat(schemas).contains(
            "identity",
            "club",
            "training",
        )
    }

    @Test
    fun `generates account id and assigns active status by default`() {
        val accountId = insertAccount(email = "first@example.com")

        val status =
            jdbcTemplate.queryForObject(
                """
                SELECT status::text
                FROM identity.account
                WHERE id = ?
                """.trimIndent(),
                String::class.java,
                accountId,
            )

        assertThat(accountId).isPositive()
        assertThat(status).isEqualTo("ACTIVE")
    }

    @Test
    fun `does not allow accounts with duplicate email`() {
        insertAccount(email = "duplicate@example.com")

        assertThrows<DataIntegrityViolationException> {
            insertAccount(email = "duplicate@example.com")
        }
    }

    @Test
    fun `does not allow unsupported account status`() {
        assertThrows<DataAccessException> {
            jdbcTemplate.update(
                """
                INSERT INTO identity.account (
                    email,
                    name,
                    surname,
                    password_hash,
                    status
                )
                VALUES (?, ?, ?, ?, ?::identity.account_status)
                """.trimIndent(),
                "invalid-status@example.com",
                "Ivan",
                "Ivanov",
                "password-hash",
                "UNKNOWN",
            )
        }
    }

    private fun insertAccount(email: String): Long =
        requireNotNull(
            jdbcTemplate.queryForObject(
                """
                INSERT INTO identity.account (
                    email,
                    name,
                    surname,
                    password_hash
                )
                VALUES (?, ?, ?, ?)
                RETURNING id
                """.trimIndent(),
                Long::class.java,
                email,
                "Ivan",
                "Ivanov",
                "password-hash",
            ),
        )
}
