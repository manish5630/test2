package com.example.data

data class PortfolioDto(
    val avatarUri: String,
    val firstName: String,
    val emailAddress: String,
    val password: String,
    val webSite: String,
) {
    companion object {
        val empty = PortfolioDto(
            avatarUri = "",
            firstName = "",
            emailAddress = "",
            password = "",
            webSite = ""
        )
    }
}