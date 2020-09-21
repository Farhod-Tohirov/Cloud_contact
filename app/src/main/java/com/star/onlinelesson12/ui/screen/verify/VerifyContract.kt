package com.star.onlinelesson11.ui.screens.verify

import com.star.onlinelesson11.model.ResponseData
import com.star.onlinelesson11.model.SmsCodeData

interface VerifyContract {
    interface Model {
        fun verifyPhone(smsCodeData: SmsCodeData, block: (ResponseData<String>) -> Unit)
        fun resendSms(phoneNumber:String,block: (ResponseData<String>) -> Unit)
        fun saveTokenToLocalStorage(data: String, block: (String) -> Unit)
    }

    interface View {
        fun loadViews()
        fun showMessage(message: String)
        fun openContactsActivity()
        fun showProgress(isShown: Boolean)
    }

    interface Presenter {
        fun completeEnterCode(smsCodeData: SmsCodeData)
        fun clickResend(phoneNumber: String)
    }
}