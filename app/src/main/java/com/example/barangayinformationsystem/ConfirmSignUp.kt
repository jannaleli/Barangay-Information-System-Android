package com.example.barangayinformationsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.mobile.config.AWSConfiguration


class ConfirmSignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up)

        var confirmButton = findViewById(R.id.confirmButton) as Button // Add this
        var confirmCode = findViewById(R.id.confirmCode) as TextView // Add this
        var confirmCodeString = confirmCode.text.toString()
        AWSMobileClient.getInstance().confirmSignUp("usernameHere", confirmCodeString,
            object : Callback<SignUpResult> /*Callback<SignUpResult!>()*/ {
                override fun onResult(signUpResult: SignUpResult) {
                    runOnUiThread {
                        // Log.d(
                        //     FragmentActivity.TAG,
                        //     "Sign-up callback state: " + signUpResult.getConfirmationState()
                        // )
                        if (!signUpResult.getConfirmationState()) {

                        } else {
                            // makeToast("Sign-up done.")
                            val details = signUpResult.getUserCodeDeliveryDetails()
                            //  makeToast("Confirm sign-up with: " + details.getDestination())
                            val intent = Intent(this@ConfirmSignUp, LoginUser::class.java)    //(this, SignInActivity::class.java)
                            // start your next activity
                            startActivity(intent)
                        }
                    }
                }

                override fun onError(e: Exception) {
                    //   Log.e(FragmentActivity.TAG, "Sign-up error", e)
                }
            }   )
    }
}
