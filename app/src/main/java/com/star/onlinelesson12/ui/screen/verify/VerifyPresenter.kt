package com.star.onlinelesson11.ui.screens.verify

import com.star.onlinelesson11.model.SmsCodeData

class VerifyPresenter(
    private val view: VerifyContract.View,
    private val model: VerifyContract.Model
) : VerifyContract.Presenter {

    init {
        view.loadViews()
    }

    override fun completeEnterCode(smsCodeData: SmsCodeData) {
        view.showProgress(true)
        model.verifyPhone(smsCodeData) {
            view.showProgress(false)
            if (it.status == "OK") {
                model.saveTokenToLocalStorage(it.data.toString()) {
                    if (it == "OK") view.openContactsActivity() else view.showMessage("There is error on something")
                }
            } else {
                view.showMessage(it.message)
            }
        }
    }

    override fun clickResend(phoneNumber: String) {
        view.showProgress(true)
        model.resendSms(phoneNumber) {
            view.showProgress(false)
            view.showMessage(it.message)
        }
    }
}