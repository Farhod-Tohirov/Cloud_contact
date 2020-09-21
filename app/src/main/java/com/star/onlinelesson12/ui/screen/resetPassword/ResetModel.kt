package com.star.onlinelesson12.ui.screen.resetPassword

import com.star.onlinelesson11.data.restAPI.UserDataApi
import com.star.onlinelesson11.model.PhoneNumber
import com.star.onlinelesson11.model.ResetData
import com.star.onlinelesson11.model.ResponseData
import com.star.onlinelesson11.ui.screens.resetPassword.ResetContract
import com.star.onlinelesson12.data.restAPI.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetModel : ResetContract.Model {

    private val api = ApiClient.createService(UserDataApi::class.java)

    override fun sendPhoneNumber(phoneNumber: String, block: (ResponseData<String>) -> Unit) {
        val newPhoneNumber = PhoneNumber(phoneNumber)
        api.sendPhoneNumber(newPhoneNumber)
            .enqueue(object : Callback<ResponseData<String>> {
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
                            "Server is switched off or changed"
                        )
                    ) else {
                        val res = response.body()
                        if (res != null) block(res)
                    }
                }
            })
    }

    override fun resetUserPassword(resetData: ResetData, block: (ResponseData<Any>) -> Unit) {
        api.resetPassword(resetData).enqueue(object : Callback<ResponseData<Any>> {
            override fun onFailure(call: Call<ResponseData<Any>>, t: Throwable) {
                block(ResponseData("FAILURE", "Check the internet connection!"))
            }

            override fun onResponse(
                call: Call<ResponseData<Any>>,
                response: Response<ResponseData<Any>>
            ) {
                val res = response.body()
                if (res != null) block(res)
            }
        })
    }
}