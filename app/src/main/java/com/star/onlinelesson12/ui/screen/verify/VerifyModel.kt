package com.star.onlinelesson11.ui.screens.verify

import com.star.onlinelesson11.data.LocalStorage
import com.star.onlinelesson11.data.restAPI.UserDataApi
import com.star.onlinelesson11.model.PhoneNumber
import com.star.onlinelesson11.model.ResponseData
import com.star.onlinelesson11.model.SmsCodeData
import com.star.onlinelesson12.data.restAPI.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyModel : VerifyContract.Model {

    val api = ApiClient.createService(UserDataApi::class.java)


    override fun verifyPhone(smsCodeData: SmsCodeData, block: (ResponseData<String>) -> Unit) {
        api.verifyCode(smsCodeData).enqueue(object : Callback<ResponseData<String>> {
            override fun onFailure(call: Call<ResponseData<String>>, t: Throwable) {
                block(ResponseData("FAILURE", "Check the internet connection!"))
            }

            override fun onResponse(
                call: Call<ResponseData<String>>,
                response: Response<ResponseData<String>>
            ) {
                val res = response.body()
                if (res != null) block(res)
            }
        })
    }

    override fun resendSms(phoneNumber: String, block: (ResponseData<String>) -> Unit) {
        api.resendSms(PhoneNumber(phoneNumber)).enqueue(object : Callback<ResponseData<String>> {
            override fun onFailure(call: Call<ResponseData<String>>, t: Throwable) {
                block(ResponseData("FAILURE", "Check the internet connection!"))
            }

            override fun onResponse(
                call: Call<ResponseData<String>>,
                response: Response<ResponseData<String>>
            ) {
                if (response.code() >= 500) block(
                    ResponseData(
                        "FAILURE",
                        "Server is switched of or changed"
                    )
                ) else {
                    val res = response.body()
                    if (res != null) block(res)
                }
            }
        })
    }

    override fun saveTokenToLocalStorage(data: String, block: (String) -> Unit) {
        if (data.isEmpty()) block("NO") else block("OK")
        LocalStorage.instance.token = data
    }


}