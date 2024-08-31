package com.shipsmart.domain.usecases

import android.util.Log
import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.repository.InputDataChecker
import com.shipsmart.domain.repository.UserRepositoryInterface

class SignInUseCase(private val userRepository: UserRepositoryInterface) {
    private val inputDataChecker = InputDataChecker()

    suspend fun execute(email: String, password: String): Boolean {
        Log.d(TEST, "login was called")

        val regParams = RegistrationParams(email, password)
        val dataIsValid = inputDataChecker.inputDataIsValid(regParams)
        if (!dataIsValid) return false

        val user = userRepository.getUser(regParams)
        return !(user == null || user.password != regParams.password)
    }

    companion object {
        const val TAG = "SignUpUseCase"
        const val ERROR = "SignUpUseCase.error"
        const val TEST = "SignUpUseCaseTest"
    }
}