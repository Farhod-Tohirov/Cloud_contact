package com.star.onlinelesson12.ui.screen.login

import com.star.onlinelesson11.model.LoginData
import com.star.onlinelesson11.model.ResponseData

interface LoginContract {
    interface Model {
        fun getToken(loginData: LoginData, block: (ResponseData<String>) -> Unit)
        fun saveTokenToLocalStorage(token: String)
    }

    interface View {
        fun loadViews()
        fun showMessage(message: String)
        fun openContactsActivity()
        fun showProgress(isShown: Boolean)
    }

    interface Presenter {
        fun clickLoginButton(loginData: LoginData)
    }
}