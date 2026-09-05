package io.github.RusMar21.roundmate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RoundmateApplication

fun main(args: Array<String>) {
	runApplication<RoundmateApplication>(*args)
}
