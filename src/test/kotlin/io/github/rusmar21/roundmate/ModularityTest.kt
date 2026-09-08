package io.github.rusmar21.roundmate

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules

class ModularityTest {
    private val modules = ApplicationModules.of(RoundmateApplication::class.java)

    @Test
    @Disabled("Enable after adding real modules with functionality")
    fun `detect expected modules`() {
        val actualModules = modules.map { it.identifier.toString() }.toSet()
        assertThat(actualModules).containsExactlyInAnyOrder(
            "identity",
            "club",
            "training",
        )
    }

    @Test
    fun `verify modules boundaries`() {
        modules.verify()
    }
}
