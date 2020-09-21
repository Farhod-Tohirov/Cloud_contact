package com.star.onlinelesson12.ui.screen.verify

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.onlinelesson11.model.SmsCodeData
import com.star.onlinelesson11.ui.screens.verify.VerifyContract
import com.star.onlinelesson11.ui.screens.verify.VerifyModel
import com.star.onlinelesson11.ui.screens.verify.VerifyPresenter
import com.star.onlinelesson12.R
import com.star.onlinelesson12.ui.screen.contacts.ContactsFragment
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyFragment : Fragment(R.layout.activity_verify), VerifyContract.View {

    private lateinit var presenter: VerifyPresenter
    private lateinit var phone: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = this.arguments
        phone = bundle?.getString("PHONE").toString()
        presenter = VerifyPresenter(this, VerifyModel())
    }

    override fun loadViews() {
        val toolbar = activity?.verifyToolbar
        toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
        buttonResend.setOnClickListener { presenter.clickResend(phone) }
        verifyPhone.text = phone
        verifyCode.setOtpCompletionListener {
            presenter.completeEnterCode(SmsCodeData(phone, it))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun openContactsActivity() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.mainActivity, ContactsFragment())
            ?.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            ?.commit()
    }

    override fun showProgress(isShown: Boolean) {
        showProgress.visibility = if (isShown) View.VISIBLE else View.INVISIBLE
    }

}