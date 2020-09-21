package com.star.onlinelesson12.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.star.onlinelesson11.data.LocalStorage
import com.star.onlinelesson12.R
import com.star.onlinelesson12.ui.screen.contacts.ContactsFragment
import com.star.onlinelesson12.ui.screen.login.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = if (LocalStorage.instance.isRemembered) {
            LocalStorage.instance.isRemembered = false;ContactsFragment()
        } else LoginFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainActivity, fragment)
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        transaction.commit()
    }

}