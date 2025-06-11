package com.shipsmartapp.login.domain.usecases

import android.util.Log
import com.shipsmartapp.login.data.states.InputDataState
import com.core.db_network.data.storage.RegistrationParams
import com.shipsmartapp.login.data.states.UserDataState
import com.shipsmartapp.login.domain.repository.InputDataChecker
import com.shipsmartapp.login.domain.repository.UserRepositoryInterface

class SignInUseCase(private val userRepository: UserRepositoryInterface) {

    suspend fun execute(email: String, password: String): InputDataState {
        Log.d(TAG, "login was called")

        val regParams =
            RegistrationParams(email = email, password = password)
        return when(val dataState = InputDataChecker().dataIsValid(params=regParams)) {
            is InputDataState.ErrorState ->
                dataState

            is InputDataState.AcceptState ->
                checkPassword(regParams)
        }
    }

    private suspend fun checkPassword(params: RegistrationParams): InputDataState {
        Log.d(TAG, params.toString())

        when (val userDataState = userRepository.getUser(params)) {
            is UserDataState.ErrorState ->
                return InputDataState.ErrorState(userDataState.message)

            is UserDataState.AcceptState -> {
                Log.d(TAG, userDataState.toString())
                if (userDataState.regParams != null &&
                    userDataState.regParams.password == params.password) {
                    return InputDataState.AcceptState(params.email)
                }

                val message = "Password is incorrect"
                return InputDataState.ErrorState(message)
            }
        }
    }

    companion object {
        const val TAG = "SignUpUseCase"
    }
}