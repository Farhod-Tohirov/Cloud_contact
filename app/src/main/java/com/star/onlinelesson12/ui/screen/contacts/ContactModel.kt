package com.star.onlinelesson12.ui.screen.contacts

import android.os.Handler
import android.os.Looper
import com.star.contactsrestapi2.data.local.room.AppDatabase
import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.contactsrestapi2.utils.ResultData
import com.star.onlinelesson11.data.restAPI.UserDataApi
import com.star.onlinelesson11.model.ResponseData
import com.star.onlinelesson11.ui.screens.contacts.ContactContract
import com.star.onlinelesson12.R
import com.star.onlinelesson12.app.App
import com.star.onlinelesson12.data.restAPI.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class ContactModel : ContactContract.Model {

    private val contactDao = AppDatabase.getDatabase(App.instance).contactDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())
    private val api = ApiClient.createServiceWithAuth(UserDataApi::class.java)

    override fun saveToDB(ls: List<ContactData>) {
        runOnWorkerThread { contactDao.insertAll(ls) }
    }

    override fun getFromDB(block: (List<ContactData>) -> Unit) {
        var ls: List<ContactData>
        runOnWorkerThread {
            ls = contactDao.getAllContacts()
            block(ls)
        }
    }

    override fun getFromServer(block: (ResultData<List<ContactData>>) -> Unit) {
        api.getAllContacts().enqueue(object : Callback<ResponseData<List<ContactData>>> {
            override fun onFailure(call: Call<ResponseData<List<ContactData>>>, t: Throwable) {
                block(ResultData.failure(t))
            }

            override fun onResponse(
                call: Call<ResponseData<List<ContactData>>>,
                response: Response<ResponseData<List<ContactData>>>
            ) {
                if (!response.isSuccessful) return
                if (response.code() >= 500) block(ResultData.message("Server is not found")) else {
                    val res = response.body()
                    when {
                        res == null -> block(ResultData.resource(R.string.alertNull))
                        res.status == "ERROR" -> block(ResultData.message(res.message))
                        res.status == "OK" -> block(ResultData.data(res.data ?: emptyList()))
                    }
                }
            }
        })
    }

    override fun removeFromServer(
        contactData: ContactData,
        block: (ResultData<ContactData>) -> Unit
    ) {
        api.remove(contactData).enqueue(object : Callback<ResponseData<ContactData>> {
            override fun onFailure(call: Call<ResponseData<ContactData>>, t: Throwable) {
                block(ResultData.failure(t))
            }

            override fun onResponse(
                call: Call<ResponseData<ContactData>>,
                response: Response<ResponseData<ContactData>>
            ) {
                val res = response.body()
                if (res != null)
                    if (res.data != null)
                        block(ResultData.data(res.data))
            }
        })
    }

    override fun addToServer(contactData: ContactData, block: (ResultData<ContactData>) -> Unit) {
        api.add(contactData).enqueue(object : Callback<ResponseData<ContactData>> {
            override fun onFailure(call: Call<ResponseData<ContactData>>, t: Throwable) {
                block(ResultData.failure(t))
            }

            override fun onResponse(
                call: Call<ResponseData<ContactData>>,
                response: Response<ResponseData<ContactData>>
            ) {
                val res = response.body()
                if (res != null)
                    if (res.data != null)
                        block(ResultData.data(res.data)) else block(ResultData.message(res.message))
            }

        })
    }

    override fun addToDB(contactData: ContactData) {
        runOnWorkerThread { contactDao.insert(contactData) }
    }

    override fun removeFromDB(contactData: ContactData) {
        runOnWorkerThread { contactDao.delete(contactData) }
    }

    override fun clearDB() {
        runOnWorkerThread { contactDao.clearDB() }
    }

    override fun updateContact(contactData: ContactData, block: (ResultData<ContactData>) -> Unit) {
        api.update(contactData).enqueue(object : Callback<ResponseData<ContactData>> {
            override fun onFailure(call: Call<ResponseData<ContactData>>, t: Throwable) {
                block(ResultData.failure(t))
            }

            override fun onResponse(
                call: Call<ResponseData<ContactData>>,
                response: Response<ResponseData<ContactData>>
            ) {
                if (!response.isSuccessful) return
                val res = response.body()
                when {
                    res == null -> block(ResultData.resource(R.string.alertNull))
                    res.status == "ERROR" -> block(ResultData.message(res.message))
                    res.status == "OK" -> block(ResultData.data(res.data!!))
                }
            }
        })
    }

    private fun runOnUIThread(f: () -> Unit) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            f()
        } else {
            handler.post(f)
        }
    }

    private fun runOnWorkerThread(f: () -> Unit) {
        executor.execute(f)
    }

}