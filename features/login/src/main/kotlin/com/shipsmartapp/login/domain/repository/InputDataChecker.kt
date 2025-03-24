package com.shipsmartapp.login.domain.repository

import com.shipsmartapp.login.data.states.InputDataState
import com.core.data.RegistrationParams

class InputDataChecker {

    fun dataIsValid(params: RegistrationParams) : InputDataState {
        if (!emailIsValid(params.email)) {
            val message = "This email address is not suitable"
            return InputDataState.ErrorState(message)
        }
        if (!passwordIsValid(params.password)) {
            val message = "Your password is too simple"
            return InputDataState.ErrorState(message)
        }

        return InputDataState.AcceptState(params.email)
    }

    private fun emailIsValid(email: String): Boolean {
        if (email.isBlank() || email.isEmpty())
            return false
        if (!email.contains("@") || !email.contains("."))
            return false
        return true
    }

    private fun passwordIsValid(password: String): Boolean {
        //TODO: add password validation
        return password.length > 3
    }
}