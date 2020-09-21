package com.star.onlinelesson11.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.star.contactsrestapi.utils.SingleBlock
import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.onlinelesson12.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.add_contact_dialog.view.*

class ContactDialog(context: Context) : AlertDialog(context) {

    private val view =
        LayoutInflater.from(context).inflate(R.layout.add_contact_dialog, null, false)
    private var saveClickListener: SingleBlock<ContactData>? = null
    private var toastListener: SingleBlock<String>? = null
    private var contactData: ContactData? = null

    init {
        setView(view)
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.attributes?.windowAnimations = R.style.Animation_Design_BottomSheetDialog
        view.ccp.registerCarrierNumberEditText(view.regPhone)
        view.addContact.setOnClickListener {
            val data = contactData ?: ContactData(0, "", "", "")
            val name = view.newContactFirstName.text.toString()
            val lastName = view.newContactLastName.text.toString()
            var phoneNumber = view.regPhone.text.toString()
            if (name == "") {
                view.newContactFirstName.error = "Enter first name"
                view.newContactFirstName.requestFocus()
                return@setOnClickListener
            }
            if (lastName == "") {
                view.newContactLastName.error = "Enter last name"
                view.newContactLastName.requestFocus()
                return@setOnClickListener
            }
            if (phoneNumber == "") {
                view.regPhone.error = "Enter phone"
                view.regPhone.requestFocus()
                return@setOnClickListener
            }
            phoneNumber = phoneNumber.replace("\\s".toRegex(), "")
            phoneNumber = ccp.selectedCountryCodeWithPlus + phoneNumber
            data.firstName = name
            data.lastName = lastName
            data.phoneNumber = phoneNumber
            dismiss()
            saveClickListener?.invoke(data)
        }
        view.cancelAddContact.setOnClickListener { dismiss() }
    }

    fun setData(contact: ContactData) {
        contactData = contact
        view.newContactFirstName.setText(contact.firstName)
        view.newContactLastName.setText(contact.lastName)
        val phone = contact.phoneNumber?.removeRange(0, 4)
        view.regPhone.setText(phone)
    }

    fun setOnSaveClickListener(f: SingleBlock<ContactData>) {
        saveClickListener = f
    }

    fun setOnToastMakeListener(f: SingleBlock<String>) {
        toastListener = f
    }
}