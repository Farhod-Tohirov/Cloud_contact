package com.star.onlinelesson12.ui.screen.contacts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.contactsrestapi2.utils.MessageData
import com.star.onlinelesson11.data.LocalStorage
import com.star.onlinelesson11.ui.dialog.ContactDialog
import com.star.onlinelesson11.ui.screens.contacts.ContactContract
import com.star.onlinelesson11.ui.screens.contacts.ContactPresenter
import com.star.onlinelesson12.R
import com.star.onlinelesson12.ui.adapter.ContactAdapter
import com.star.onlinelesson12.ui.screen.login.LoginFragment
import kotlinx.android.synthetic.main.activity_contacts.*
import retrofit2.HttpException

class ContactsFragment : Fragment(R.layout.activity_contacts), ContactContract.View {

    private val adapter = ContactAdapter()
    private val presenter by lazy { ContactPresenter(ContactModel(), this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.init()
    }

    override fun loadViews() {
        if (LocalStorage.instance.isChecked) {
            LocalStorage.instance.isRemembered = true
            LocalStorage.instance.isChecked = false
        }
        list.layoutManager = LinearLayoutManager(context!!)
        list.adapter = adapter
        addNewContact.setOnClickListener { presenter.clickAddContactButton() }
        adapter.setOnDeleteContactListener { presenter.removeContact(it) }
        adapter.setOnEditContactListener { presenter.clickEditContactButton(it) }
        leaveContacts.setOnClickListener {
            val dialog = AlertDialog.Builder(context!!).create()
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { _, _ ->
                LocalStorage.instance.isRemembered = false
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.mainActivity, LoginFragment())
                    ?.commit()
            }
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            dialog.setTitle("Do you want to leave?")
            dialog.show()
        }
    }

    override fun loadContacts(ls: List<ContactData>) {
        adapter.submitList(ls.toMutableList())
    }

    override fun notifyAddContact(contactData: ContactData) {
        val ls = adapter.currentList.toMutableList()
        ls.add(contactData)
        adapter.submitList(ls)
    }

    override fun notifyRemoveContact(contactData: ContactData) {
        val ls = adapter.currentList.toMutableList()
        ls.remove(contactData)
        adapter.submitList(ls)
    }

    override fun openAddContactDialog() {
        val dialog = ContactDialog(context!!)
        dialog.setOnSaveClickListener { presenter.addContact(it) }
        dialog.setOnToastMakeListener { showMessageData(MessageData.message(it)) }
        dialog.show()
    }

    override fun openEditContactDialog(contactData: ContactData) {
        val dialog = ContactDialog(context!!)
        dialog.setData(contactData)
        dialog.setOnSaveClickListener { presenter.updateContact(it) }
        dialog.setOnToastMakeListener { showMessageData(MessageData.message(it)) }
        dialog.show()
    }

    override fun notifyUpdateContact(contactData: ContactData) {
        val ls = adapter.currentList.toMutableList()
        val index = ls.indexOf(contactData)
        ls[index] = contactData
        adapter.submitList(ls.toMutableList())
        adapter.notifyItemChanged(index)
    }

    override fun showMessageData(message: MessageData) {
        var text = ""
        message.onMessage {
            text = it
        }.onFailure {
            text = when (it) {
                is HttpException -> "Error with internet connection"
                else -> it.toString()
            }
        }.onResource {
            text = getString(it)
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun showLoader(boolean: Boolean) {
        progressBar.visibility = if (boolean) View.VISIBLE else View.GONE
    }
}