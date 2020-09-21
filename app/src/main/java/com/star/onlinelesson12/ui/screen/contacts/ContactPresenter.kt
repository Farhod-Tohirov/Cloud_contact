package com.star.onlinelesson11.ui.screens.contacts

import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.contactsrestapi2.utils.MessageData

class ContactPresenter(
    private val model: ContactContract.Model,
    private val view: ContactContract.View
) : ContactContract.Presenter {
    override fun init() {
        view.loadViews()
        view.showLoader(true)
        model.getFromServer {
            view.showLoader(false)
            it.onData { list ->
                view.loadContacts(list)
                view.showMessageData(MessageData.message("Loaded from server"))
                model.clearDB()
                model.saveToDB(list)
            }.onMessageData(view::showMessageData)
                .onFailure {
                    view.showMessageData(MessageData.message("Loaded from DB"))
                    model.getFromDB { list ->
                        view.loadContacts(list)
                    }
                }
                .onResource { view.showMessageData(MessageData.resource(it)) }
        }
    }

    override fun clickAddContactButton() {
        view.openAddContactDialog()
    }

    override fun addContact(contactData: ContactData) {
        model.addToServer(contactData) {
            it.onData { newContact ->
                contactData.id = newContact.id
                view.notifyAddContact(contactData)
                view.showMessageData(MessageData.message("Added successfully"))
                model.addToDB(contactData)
            }.onFailure { t -> view.showMessageData(MessageData.failure(t)) }
                .onResource { view.showMessageData(MessageData.resource(it)) }
                .onMessage { view.showMessageData(MessageData.message(it)) }
        }
    }

    override fun removeContact(contactData: ContactData) {
        model.removeFromServer(contactData) {
            model.removeFromDB(contactData)
            it.onData {
                view.notifyRemoveContact(contactData)
                view.showMessageData(MessageData.message("Deleted successfully"))
            }.onFailure { t -> view.showMessageData(MessageData.failure(t)) }
                .onResource { view.showMessageData(MessageData.resource(it)) }
        }
    }

    override fun clickEditContactButton(contactData: ContactData) {
        view.openEditContactDialog(contactData)
    }

    override fun updateContact(contactData: ContactData) {
        model.updateContact(contactData) {
            it.onData {
                view.notifyUpdateContact(it)
                view.showMessageData(MessageData.message("Updated successfully"))
            }.onFailure { t -> view.showMessageData(MessageData.failure(t)) }
                .onMessage { view.showMessageData(MessageData.message(it)) }
                .onResource { view.showMessageData(MessageData.resource(it)) }
        }
    }
}