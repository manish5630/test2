package com.example.domain

data class Portfolio(
    val avatarUri: String,
    val firstName: String,
    val emailAddress: String,
    val password: String,
    val webSite: String,
) {
    companion object {
        val empty = Portfolio(
            avatarUri = "",
            firstName = "",
            emailAddress = "",
            password = "",
            webSite = ""
        )
    }
}