package io.github.rusmar21.roundmate.identity.infrastructure.security.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder

@Configuration(proxyBeanMethods = false)
class PasswordSecurityConfiguration {
    @Bean
    fun passwordEncoder(): PasswordEncoder = Argon2Password4jPasswordEncoder()
}
