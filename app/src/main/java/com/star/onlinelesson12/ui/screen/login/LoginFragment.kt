package com.star.onlinelesson12.ui.screen.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.star.onlinelesson11.data.LocalStorage
import com.star.onlinelesson11.model.LoginData
import com.star.onlinelesson12.R
import com.star.onlinelesson12.ui.screen.contacts.ContactsFragment
import com.star.onlinelesson12.ui.screen.registration.RegistrationFragment
import com.star.onlinelesson12.ui.screen.resetPassword.ResetFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginFragment : Fragment(R.layout.activity_login), LoginContract.View {

    private lateinit var presenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = LoginPresenter(this, LoginModel())
    }

    override fun loadViews() {
        ccp.registerCarrierNumberEditText(regPhone)
        loginForgetPassword.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.mainActivity, ResetFragment())
                ?.addToBackStack("Login")
                ?.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                ?.commit()
        }
        buttonLogin.setOnClickListener {
            var phone = regPhone.text.toString()
            if (phone == "") {
                regPhone.error = "Enter phone"
                regPhone.requestFocus()
                return@setOnClickListener
            } else {
                phone = phone.replace("\\s".toRegex(), "")
                phone = ccp.selectedCountryCodeWithPlus + phone
            }
            val password = loginPassword.text.toString()
            if (password.isEmpty()) {
                loginPassword.error = "Please enter password"
                loginPassword.requestFocus()
                return@setOnClickListener
            }
            if (rememberMe.isChecked) LocalStorage.instance.isChecked = true
            presenter.clickLoginButton(LoginData(phone, password))

        }
        buttonRegistration.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.mainActivity, RegistrationFragment())
                ?.addToBackStack("Login")
                ?.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                ?.commit()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun openContactsActivity() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.mainActivity, ContactsFragment())
            ?.commit()
    }

    override fun showProgress(isShown: Boolean) {
        showProgress.visibility = if (isShown) View.VISIBLE else View.INVISIBLE
    }
}