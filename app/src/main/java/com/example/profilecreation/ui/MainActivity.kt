package com.example.profilecreation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.profilecreation.R
import com.example.profilecreation.ui.confirmation.ConfirmationFragment
import com.example.profilecreation.ui.signUp.SignUpFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openSignUpFragment()
        }
    }

    fun openConfirmationFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ConfirmationFragment.newInstance())
            .commitNow()
    }

    fun openSignUpFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignUpFragment.newInstance())
            .commitNow()
    }
}