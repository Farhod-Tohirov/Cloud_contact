package com.star.onlinelesson11.ui.screens.registration

import com.star.onlinelesson11.model.ContactUserData

class RegPresenter (private val view: RegistrationContract.View, private val model: RegistrationContract.Model):RegistrationContract.Presenter{

    init {
        view.loadViews()
    }

    override fun clickRegisterButton(userData: ContactUserData) {
        view.showProgress(true)
        model.insertUserToServer(userData){
            view.showProgress(false)
            if (it.status == "FAILURE") {
                view.showMessage("Please check  the internet connection")
                return@insertUserToServer
            }
            if (it.status == "OK"){
                view.openVerificationActivity()
                return@insertUserToServer
            } else{
                view.showMessage(it.message)
                return@insertUserToServer
            }
        }
    }
}