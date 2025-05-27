package com.example.taskmanager.domain.usecase.utils

import com.example.taskmanager.core.util.TrimWhiteSpaces

class TrimWhiteSpacesUseCase(private val trimWhiteSpaces: TrimWhiteSpaces) {
    operator fun invoke(string: String) = trimWhiteSpaces.trimExtraWhitespaces(string)
}