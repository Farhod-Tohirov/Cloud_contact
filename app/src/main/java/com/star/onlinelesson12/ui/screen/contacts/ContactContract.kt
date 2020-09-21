package com.star.onlinelesson11.ui.screens.contacts

import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.contactsrestapi2.utils.MessageData
import com.star.contactsrestapi2.utils.ResultData

interface ContactContract {
    interface Model {
        fun saveToDB(ls: List<ContactData>)
        fun getFromDB(block: (List<ContactData>) -> Unit)
        fun getFromServer(block: (ResultData<List<ContactData>>) -> Unit)
        fun removeFromServer(contactData: ContactData, block: (ResultData<ContactData>) -> Unit)
        fun addToServer(contactData: ContactData, block: (ResultData<ContactData>) -> Unit)
        fun addToDB(contactData: ContactData)
        fun removeFromDB(contactData: ContactData)
        fun clearDB()
        fun updateContact(contactData: ContactData, block: (ResultData<ContactData>) -> Unit)
    }

    interface View {
        fun loadContacts(ls: List<ContactData>)
        fun notifyAddContact(contactData: ContactData)
        fun showLoader(boolean: Boolean)
        fun notifyRemoveContact(contactData: ContactData)
        fun showMessageData(message: MessageData)
        fun openAddContactDialog()
        fun openEditContactDialog(contactData: ContactData)
        fun notifyUpdateContact(contactData: ContactData)
        fun loadViews()
    }

    interface Presenter {
        fun init()
        fun clickAddContactButton()
        fun addContact(contactData: ContactData)
        fun removeContact(contactData: ContactData)
        fun clickEditContactButton(contactData: ContactData)
        fun updateContact(contactData: ContactData)
    }
}