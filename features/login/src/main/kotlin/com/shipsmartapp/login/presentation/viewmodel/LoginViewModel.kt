package com.shipsmartapp.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shipsmartapp.login.data.states.InputDataState
import com.shipsmartapp.login.domain.usecases.SignInUseCase
import com.shipsmartapp.login.domain.usecases.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel: ViewModel() {
    @Inject
    lateinit var signInUseCase: SignInUseCase

    @Inject
    lateinit var signUpUseCase: SignUpUseCase

    private val _resultMessage = MutableSharedFlow<InputDataState>()
    val loginResultMessage: SharedFlow<InputDataState> = _resultMessage

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dataState = signInUseCase.execute(email, password)
            _resultMessage.emit(dataState)
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dataState = signUpUseCase.execute(email, password)
            _resultMessage.emit(dataState)
        }
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}