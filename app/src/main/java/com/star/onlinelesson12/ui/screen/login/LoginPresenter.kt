package com.star.onlinelesson12.ui.screen.login

import com.star.onlinelesson11.model.LoginData

class LoginPresenter(private val view: LoginContract.View, private val model: LoginContract.Model) : LoginContract.Presenter{

    init {
        view.loadViews()
    }

    override fun clickLoginButton(loginData: LoginData) {
        view.showProgress(true)
        model.getToken(loginData){
            view.showProgress(false)
            if (it.status == "OK"){
                model.saveTokenToLocalStorage(it.data.toString())
                view.showMessage(it.message)
                view.openContactsActivity()
            } else {
                view.showMessage(it.message)
            }
        }
    }
}