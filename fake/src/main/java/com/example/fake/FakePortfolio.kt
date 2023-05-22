package com.example.fake

import com.example.domain.Portfolio

fun getFakePortfolio(
    avatarUri: String = "path://",
    firstName: String = "John",
    emailAddress: String = "John@gmail.com",
    password: String = "123456",
    webSite: String = "http://www.google.com"
) = Portfolio(
    avatarUri = avatarUri,
    firstName = firstName,
    emailAddress = emailAddress,
    password = password,
    webSite = webSite,
)