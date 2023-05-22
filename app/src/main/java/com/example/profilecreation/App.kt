package com.example.profilecreation

import android.app.Application
import com.example.data.DefaultUserRepository
import com.example.data.UserService

class App : Application() {

    val userService = UserService(DefaultUserRepository())

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}