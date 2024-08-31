package com.shipsmart.domain.usecases

import android.util.Log
import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.repository.InputDataChecker
import com.shipsmart.domain.repository.UserRepositoryInterface

class SignUpUseCase(private val userRepository: UserRepositoryInterface) {
    private val inputDataChecker = InputDataChecker()

    suspend fun execute(email: String, password: String) {
        Log.d(TEST, "signup was called")

        val regParams = RegistrationParams(email, password)

        if (!inputDataChecker.inputDataIsValid(regParams)) return

        userRepository.createNewUser(regParams)
    }

    companion object {
        const val TAG = "SignInUseCase"
        const val ERROR = "SignInUseCase.error"
        const val TEST = "SignInUseCaseTest"
    }
}