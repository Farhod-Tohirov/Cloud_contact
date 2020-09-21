package com.star.onlinelesson11.ui.screens.resetPassword

import com.star.onlinelesson11.model.ResetData

class ResetPresenter(private val view: ResetContract.View, private val model: ResetContract.Model) :
    ResetContract.Presenter {
    init {
        view.loadViews()
    }

    override fun clickSendCode(phoneNumber: String) {
        view.showProgress(true)
        model.sendPhoneNumber(phoneNumber) {
            view.showProgress(false)
            if (it.status == "OK") {
                view.showMessage(it.message)
                view.showConfirmPart()
            } else {
                view.showMessage(it.message)
            }
        }
    }

    override fun clickConfirm(resetData: ResetData) {
        view.showProgress(true)
        model.resetUserPassword(resetData) {
            view.showProgress(false)
            if (it.status == "OK") {
                view.showMessage(it.message)
                view.closeReset()
            } else {
                view.showMessage(it.message)
            }
        }
    }
}