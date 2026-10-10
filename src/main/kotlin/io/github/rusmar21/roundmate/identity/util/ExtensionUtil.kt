package io.github.rusmar21.roundmate.identity.util

import io.github.rusmar21.roundmate.identity.exception.InvalidFieldFormatException

fun String.normalizeAndValidate(): String {
    require(this.trim().isNotBlank()) { throw InvalidFieldFormatException() }
    return this.trim()
}
