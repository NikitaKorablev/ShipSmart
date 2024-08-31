package com.shipsmart.domain.usecases

import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.repository.ToastConstructor

class InputDataChecker {

    fun inputDataIsValid(params: RegistrationParams) : Boolean {
        return emailIsValid(params.email) && passwordIsValid(params.password)
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