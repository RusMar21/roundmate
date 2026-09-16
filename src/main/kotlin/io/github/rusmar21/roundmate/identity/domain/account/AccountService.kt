package io.github.rusmar21.roundmate.identity.domain.account

import io.github.rusmar21.roundmate.identity.domain.account.model.RegisterAccountRequest
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AccountService {
    fun registerAccount(registerAccountRequest: RegisterAccountRequest): UUID {
        // TODO: заглушка для RED теста
        return UUID.randomUUID()
    }
}