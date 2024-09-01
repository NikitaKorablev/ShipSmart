package com.shipsmart.domain.usecases

import android.util.Log
import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.repository.InputDataChecker
import com.shipsmart.domain.repository.UserRepositoryInterface

class SignUpUseCase(private val userRepository: UserRepositoryInterface) {
    private val inputDataChecker = InputDataChecker()

    suspend fun execute(email: String, password: String): Boolean {
        Log.d(TEST, "signup was called")

        val regParams = RegistrationParams(email, password)
        val dataIsValid = inputDataChecker.inputDataIsValid(regParams)
        if (!dataIsValid) return false

        val user = userRepository.createNewUser(regParams)
        return !(user == null || user.password != regParams.password || user.email != regParams.email)
    }

    companion object {
        const val TAG = "SignInUseCase"
        const val ERROR = "SignInUseCase.error"
        const val TEST = "SignInUseCaseTest"
    }
}