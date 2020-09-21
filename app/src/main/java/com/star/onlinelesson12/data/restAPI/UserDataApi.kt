package com.star.onlinelesson11.data.restAPI

import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.onlinelesson11.model.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface UserDataApi {
    @POST("contact/register")
    fun insertUser(@Body contactUserData: ContactUserData): Call<ResponseData<String>>

    @POST("contact/verify")
    fun verifyCode(@Body smsCodeData: SmsCodeData): Call<ResponseData<String>>

    @POST("contact/login")
    fun getToken(@Body loginData: LoginData): Call<ResponseData<String>>

    @POST("contact/resend")
    fun resendSms(@Body phoneNumber: PhoneNumber): Call<ResponseData<String>>

    @POST("contact/password")
    fun resetPassword(@Body resetData: ResetData): Call<ResponseData<Any>>

    @POST("contact/reset")
    fun sendPhoneNumber(@Body phoneNumber: PhoneNumber): Call<ResponseData<String>>

    @GET("contact")
    fun getAllContacts(): Call<ResponseData<List<ContactData>>>

    @POST("contact")
    fun add(@Body contactData: ContactData): Call<ResponseData<ContactData>>

    @HTTP(method = "DELETE", path = "contact", hasBody = true)
    fun remove(@Body contactData: ContactData): Call<ResponseData<ContactData>>

    @PUT("contact")
    fun update(@Body contactData: ContactData): Call<ResponseData<ContactData>>
}