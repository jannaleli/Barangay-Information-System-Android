package com.example.barangayinformationsystem

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.*
import com.amazonaws.mobile.config.AWSConfiguration
import android.util.Log

class SignInActivity : Activity() {
    companion object {
        private val TAG: String = this::class.java.simpleName
    }

    private var credentialsProvider: AWSCredentialsProvider? = null
    private var awsConfiguration: AWSConfiguration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        AWSMobileClient.getInstance().initialize(this) {
//            val ui = AWSMobileClient.getInstance().getClient(
//                this@SignInActivity,
//                SignInUI::class.java) as SignInUI?
//            ui?.login(
//                this@SignInActivity,
//                LoginUser::class.java)?.execute()
//        }.execute()

}

    }


