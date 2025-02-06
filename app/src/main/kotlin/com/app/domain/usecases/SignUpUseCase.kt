package com.app.domain.usecases

import android.util.Log
import com.app.domain.model.RegistrationParams
import com.app.domain.repository.InputDataChecker
import com.app.domain.repository.UserRepositoryInterface

class SignUpUseCase(private val userRepository: UserRepositoryInterface) {
    private val inputDataChecker = InputDataChecker()

    suspend fun execute(email: String, password: String): Boolean {
        Log.d(TEST, "signup was called")

        val regParams = RegistrationParams(email=email, password=password)
        val dataIsValid = inputDataChecker.inputDataIsValid(regParams)
        if (!dataIsValid) return false

        val res: Boolean = userRepository.saveUser(regParams)
        return res
    }

    companion object {
        const val TAG = "SignInUseCase"
        const val ERROR = "SignInUseCase.error"
        const val TEST = "SignInUseCaseTest"
    }
}