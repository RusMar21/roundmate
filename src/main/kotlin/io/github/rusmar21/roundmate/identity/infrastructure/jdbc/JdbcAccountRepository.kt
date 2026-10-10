package io.github.rusmar21.roundmate.identity.infrastructure.jdbc

import io.github.rusmar21.roundmate.identity.domain.account.model.Account
import io.github.rusmar21.roundmate.identity.domain.account.model.AccountId
import io.github.rusmar21.roundmate.identity.domain.account.model.Email
import io.github.rusmar21.roundmate.identity.domain.account.repository.AccountRepository
import org.springframework.stereotype.Repository

@Repository
class JdbcAccountRepository : AccountRepository {
    override suspend fun findByEmail(email: Email): Account? {
        TODO("Not yet implemented")
    }

    override suspend fun save(account: Account): AccountId {
        TODO("Not yet implemented")
    }
}
