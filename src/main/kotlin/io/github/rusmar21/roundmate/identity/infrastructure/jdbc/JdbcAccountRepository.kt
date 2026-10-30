package io.github.rusmar21.roundmate.identity.infrastructure.jdbc

import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountStatus
import io.github.rusmar21.roundmate.identity.domain.account.model.Email
import io.github.rusmar21.roundmate.identity.domain.account.model.PasswordHash
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Types

@Repository
class JdbcAccountRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : AccountRepository {
    override fun findByEmail(email: Email): Account? =
        jdbcTemplate
            .query(
                "SELECT id, status, email, name, surname, password_hash FROM identity.account WHERE email = :email".trimIndent(),
                mapOf("email" to email.value),
                accountRowMapper,
            ).firstOrNull()

    override fun save(account: Account): AccountId {
        val id =
            jdbcTemplate.queryForObject(
                """
                INSERT INTO identity.account(email, name, surname, password_hash, status)
                VALUES (:email, :name, :surname, :passwordHash, :status)
                RETURNING id
                """.trimIndent(),
                MapSqlParameterSource()
                    .addValue("email", account.email.value)
                    .addValue("name", account.name)
                    .addValue("surname", account.surname)
                    .addValue("passwordHash", account.passwordHash.hash)
                    .addValue("status", account.status.name, Types.OTHER),
                Long::class.java,
            )
        return AccountId(requireNotNull(id))
    }

    private val accountRowMapper =
        RowMapper { rs, _ ->
            Account(
                accountId = AccountId(rs.getLong("id")),
                email = Email(rs.getString("email")),
                name = rs.getString("name"),
                surname = rs.getString("surname"),
                passwordHash = PasswordHash(rs.getString("password_hash")),
                status = AccountStatus.valueOf(rs.getString("status")),
            )
        }
}
