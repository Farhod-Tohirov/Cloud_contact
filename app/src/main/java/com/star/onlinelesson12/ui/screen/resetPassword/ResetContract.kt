package com.star.onlinelesson11.ui.screens.resetPassword

import com.star.onlinelesson11.model.ResetData
import com.star.onlinelesson11.model.ResponseData

interface ResetContract {
    interface Model {
        fun sendPhoneNumber(phoneNumber: String, block: (ResponseData<String>) -> Unit)
        fun resetUserPassword(resetData: ResetData, block: (ResponseData<Any>) -> Unit)
    }

    interface View {
        fun loadViews()
        fun showMessage(message: String)
        fun showConfirmPart()
        fun closeReset()
        fun showProgress(isShown: Boolean)

    }

    interface Presenter {
        fun clickSendCode(phoneNumber: String)
        fun clickConfirm(resetData: ResetData)
    }
}