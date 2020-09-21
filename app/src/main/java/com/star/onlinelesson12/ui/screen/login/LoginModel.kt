package com.star.onlinelesson12.ui.screen.login

import com.star.onlinelesson11.data.LocalStorage
import com.star.onlinelesson11.data.restAPI.UserDataApi
import com.star.onlinelesson11.model.LoginData
import com.star.onlinelesson11.model.ResponseData
import com.star.onlinelesson12.data.restAPI.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginModel : LoginContract.Model {

    private val api = ApiClient.createService(UserDataApi::class.java)

    override fun getToken(loginData: LoginData, block: (ResponseData<String>) -> Unit) {
        api.getToken(loginData).enqueue(object : Callback<ResponseData<String>> {
            override fun onFailure(call: Call<ResponseData<String>>, t: Throwable) {
                val f = ResponseData<String>("FAILURE", t.toString())
                block(f)
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

    override fun saveTokenToLocalStorage(token: String) {
        LocalStorage.instance.token = token
    }
}