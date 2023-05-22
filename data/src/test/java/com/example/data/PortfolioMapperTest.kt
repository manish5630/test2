package com.example.data

import com.example.fake.getFakePortfolio
import org.junit.Assert
import org.junit.Test


internal class PortfolioMapperTest {

    @Test
    fun `Check PortfolioDto mapping to domain`() {

        val portfolioDto = PortfolioDto(
            avatarUri = "path://",
            firstName = "John",
            emailAddress = "John@gmail.com",
            password = "123456",
            webSite = "http://www.google.com"
        )
        val expectedPortfolio = getFakePortfolio()

        Assert.assertEquals(expectedPortfolio, portfolioDto.toDomain())
    }

    @Test
    fun `Check Portfolio mapping to dto`() {

        val portfolioDtoExpected = PortfolioDto(
            avatarUri = "path://",
            firstName = "John",
            emailAddress = "John@gmail.com",
            password = "123456",
            webSite = "http://www.google.com"
        )
        val portfolio = getFakePortfolio()

        Assert.assertEquals(portfolioDtoExpected, portfolio.toDto())
    }
}