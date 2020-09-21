package com.star.onlinelesson12.ui.screen.registration

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.onlinelesson11.model.ContactUserData
import com.star.onlinelesson11.ui.screens.registration.RegModel
import com.star.onlinelesson11.ui.screens.registration.RegPresenter
import com.star.onlinelesson11.ui.screens.registration.RegistrationContract
import com.star.onlinelesson12.R
import com.star.onlinelesson12.ui.screen.verify.VerifyFragment
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationFragment : Fragment(R.layout.activity_registration), RegistrationContract.View {

    private lateinit var presenter: RegPresenter
    private var phone: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = RegPresenter(this, RegModel())
    }

    override fun loadViews() {
        val toolbar = activity?.regToolbar
        toolbar?.setNavigationOnClickListener { activity?.onBackPressed() }
        buttonLoginRegistration.setOnClickListener { activity?.onBackPressed() }
        ccp.registerCarrierNumberEditText(regPhone)
        buttonSaveUser.setOnClickListener {
            val lastName = regLastName.text.toString()
            if (lastName == "") {
                regLastName.error = "Enter last name"
                regLastName.requestFocus()
                return@setOnClickListener
            }
            val firstName = regFirstName.text.toString()
            if (firstName == "") {
                regLastName.error = "Enter first name"
                regLastName.requestFocus()
                return@setOnClickListener
            }
            var phone = regPhone.text.toString()
            if (phone == "") {
                regPhone.error = "Enter phone"
                regPhone.requestFocus()
                return@setOnClickListener
            } else {
                phone = phone.replace("\\s".toRegex(), "")
                phone = ccp.selectedCountryCodeWithPlus + phone
            }
            val password = regPassword.text.toString()
            if (password == "") {
                regPassword.error = "Enter password"
                regPassword.requestFocus()
                return@setOnClickListener
            }
            val passwordConfirm = regPasswordConfirm.text.toString()
            if (passwordConfirm == "") {
                regPasswordConfirm.error = "Enter confirm password"
                regPasswordConfirm.requestFocus()
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                regPasswordConfirm.error = "Confirm password is not the same with password"
                regPasswordConfirm.requestFocus()
                return@setOnClickListener
            }

            this.phone = phone
            presenter.clickRegisterButton(ContactUserData(phone, password, lastName, firstName))
        }
    }

    override fun showMessage(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    override fun openVerificationActivity() {
        fragmentManager?.beginTransaction()
            ?.replace(
                R.id.mainActivity,
                VerifyFragment().apply { arguments = Bundle().apply { putString("PHONE", phone) } })
            ?.addToBackStack("Register")
            ?.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            ?.commit()
    }

    override fun showProgress(isShown: Boolean) {
        showProgress.visibility = if (isShown) View.VISIBLE else View.INVISIBLE
    }
}