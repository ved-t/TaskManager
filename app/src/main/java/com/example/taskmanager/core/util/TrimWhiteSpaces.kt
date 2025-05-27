package com.example.taskmanager.core.util

class TrimWhiteSpaces {
    fun trimExtraWhitespaces(input: String): String {
        return input.trim().replace(Regex("\\s+"), " ")
    }
}
