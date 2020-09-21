package com.star.onlinelesson11.ui.screens.registration

import com.star.onlinelesson11.model.ContactUserData
import com.star.onlinelesson11.model.ResponseData

interface RegistrationContract {
    interface Model {
        fun insertUserToServer(userData: ContactUserData, block: (ResponseData<String>) -> Unit)
    }

    interface View {
        fun loadViews()
        fun showMessage(s: String)
        fun openVerificationActivity()
        fun showProgress(isShown: Boolean)
    }

    interface Presenter {
        fun clickRegisterButton(userData: ContactUserData)

    }
}