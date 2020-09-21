package com.star.onlinelesson12.ui.screen.resetPassword

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.onlinelesson11.model.ResetData
import com.star.onlinelesson11.ui.screens.resetPassword.ResetContract
import com.star.onlinelesson11.ui.screens.resetPassword.ResetPresenter
import com.star.onlinelesson12.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetFragment : Fragment(R.layout.activity_reset_password), ResetContract.View {

    private lateinit var presenter: ResetPresenter
    private var phone = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = ResetPresenter(this, ResetModel())
    }

    override fun loadViews() {
        val toolbar = activity?.resetToolbar
        toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
        ccp.registerCarrierNumberEditText(regPhone)
        buttonSendCode.setOnClickListener {
            phone = regPhone.text.toString()
            if (phone == "") {
                regPhone.error = "Enter phone"
                regPhone.requestFocus()
                return@setOnClickListener
            } else {
                phone = phone.replace("\\s".toRegex(), "")
                phone = ccp.selectedCountryCodeWithPlus + phone
            }
            presenter.clickSendCode(phone)
        }
        buttonResetConfirm.setOnClickListener {
            val code = code.text.toString()
            val password = newPassword.text.toString()
            presenter.clickConfirm(ResetData(phone, password, code))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showConfirmPart() {
        sendPart.visibility = View.INVISIBLE
        resetPart.visibility = View.VISIBLE
    }

    override fun closeReset() {
        fragmentManager?.popBackStack()
    }

    override fun showProgress(isShown: Boolean) {
        showProgress.visibility = if (isShown) View.VISIBLE else View.INVISIBLE
    }
}