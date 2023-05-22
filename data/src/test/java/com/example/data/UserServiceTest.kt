package com.example.data

import app.cash.turbine.test
import com.example.domain.UserRepository
import com.example.fake.getFakePortfolio
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

internal class UserServiceTest {

    @Test
    fun `Check getPortfolio of UserService`() = runTest {

        val repository: UserRepository = mockk(relaxed = true)

        val expectedPortfolio = getFakePortfolio()

        every { repository.getPortfolio() } returns flowOf(expectedPortfolio)

        val service = UserService(repository)

        service.getPortfolio().test {
            Assert.assertEquals(awaitItem(), expectedPortfolio)
            awaitComplete()
        }
    }

    @Test
    fun `Check savePortfolio of UserService`() = runTest {

        val repository: UserRepository = mockk(relaxed = true)

        val expectedPortfolio = getFakePortfolio()

        every { repository.savePortfolio(expectedPortfolio) } returns Unit

        val service = UserService(repository)
        service.savePortfolio(expectedPortfolio)

        verify { repository.savePortfolio(expectedPortfolio) }
    }
}
