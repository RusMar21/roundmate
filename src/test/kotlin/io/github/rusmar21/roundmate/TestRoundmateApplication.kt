package io.github.rusmar21.roundmate

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<RoundmateApplication>().with(TestcontainersConfiguration::class).run(*args)
}
