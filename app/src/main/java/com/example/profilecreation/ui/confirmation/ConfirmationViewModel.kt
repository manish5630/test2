package com.example.profilecreation.ui.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.data.UserService
import com.example.domain.Portfolio
import kotlinx.coroutines.flow.*

class ConfirmationViewModel(userService: UserService) : ViewModel() {

    private val _uiModel: StateFlow<UIModel<Portfolio>> =
        userService.getPortfolio().map { UIModel.Data(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UIModel.Loading)

    val uiModel: Flow<UIModel<Portfolio>> = _uiModel


    @Suppress("UNCHECKED_CAST")
    class ConfirmationViewModelFactory(
        private val userService: UserService,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ConfirmationViewModel(userService) as T
        }
    }
}