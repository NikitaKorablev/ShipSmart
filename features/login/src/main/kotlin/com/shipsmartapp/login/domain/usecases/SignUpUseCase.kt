package com.shipsmartapp.login.domain.usecases

import android.util.Log
import com.shipsmartapp.login.data.states.InputDataState
import com.core.data.storage.RegistrationParams
import com.shipsmartapp.login.domain.repository.InputDataChecker
import com.shipsmartapp.login.domain.repository.UserRepositoryInterface

class SignUpUseCase(private val userRepository: UserRepositoryInterface) {

    suspend fun execute(email: String, password: String): InputDataState {
        Log.d(TAG, "signup was called")

        val regParams = RegistrationParams(email=email, password=password)
        when(val dataState = InputDataChecker().dataIsValid(params=regParams)) {
            is InputDataState.ErrorState ->
                return dataState

            is InputDataState.AcceptState -> {
                val res = userRepository.saveUser(regParams)
                if (!res) {
                    val message = "Registration failed"
                    return InputDataState.ErrorState(message)
                }
                return InputDataState.AcceptState(regParams.email)
            }
        }
    }

    companion object {
        const val TAG = "SignInUseCase"
        const val ERROR = "SignInUseCase.error"
        const val TEST = "SignInUseCaseTest"
    }
}