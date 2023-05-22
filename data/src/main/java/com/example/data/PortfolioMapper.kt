package com.example.data

import com.example.domain.Portfolio

fun PortfolioDto.toDomain() = Portfolio(avatarUri, firstName, emailAddress, password, webSite)
fun Portfolio.toDto() = PortfolioDto(avatarUri, firstName, emailAddress, password, webSite)