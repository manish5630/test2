package com.example.profilecreation

import app.cash.turbine.test
import com.example.common.MainDispatcherRule
import com.example.common.dataOrNull
import com.example.data.UserService
import com.example.fake.getFakePortfolio
import com.example.profilecreation.ui.signUp.SignUpViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class SignUpViewModelTest {

    @get:Rule
    val coroutinesTestRule = MainDispatcherRule()

    @Test
    fun `Check uiModel when viewModel created`() = runTest {

        val service: UserService = mockk(relaxed = true)

        val expectedPortfolio = getFakePortfolio()

        every { service.getPortfolio() } returns flowOf(expectedPortfolio)

        val viewModel = SignUpViewModel(service)

        viewModel.uiModel.test {
            Assert.assertEquals(awaitItem().dataOrNull(), expectedPortfolio)
        }
    }

    @Test
    fun `Check uiModel after called updateFirstName`() = runTest {

        val service: UserService = mockk(relaxed = true)
        every { service.getPortfolio() } returns flowOf(getFakePortfolio(firstName = "John"))

        val viewModel = SignUpViewModel(service)

        viewModel.updateFirstName("Anna")

        val expectedPortfolio = getFakePortfolio(firstName = "Anna")

        viewModel.uiModel.test {
            Assert.assertEquals(awaitItem().dataOrNull(), expectedPortfolio)
        }
    }
}