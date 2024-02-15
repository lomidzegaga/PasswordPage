package com.example.passwordpage

fun String.containsNumbers(): Boolean {
    val regex = Regex("\\d")
    return regex.containsMatchIn(this)
}

fun String.containsLetters(): Boolean {
    val regex = Regex("[a-zA-Z]")
    return regex.containsMatchIn(this)
}

fun String.containsSymbols(): Boolean {
    val regex = Regex("[!@#$%&><?/'\"]")
    return regex.containsMatchIn(this)
}