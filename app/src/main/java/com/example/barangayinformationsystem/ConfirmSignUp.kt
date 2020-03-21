package com.example.barangayinformationsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInState
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.mobile.config.AWSConfiguration


class ConfirmSignUp : AppCompatActivity() {
    fun showError(message: String) {

        runOnUiThread {
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle("Failure")
            //set message for alert dialog
            builder.setMessage(message)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("OK"){dialogInterface, which ->
                //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
                finish()
            }
            //performing cancel action


            // Create the AlertDialogf
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up)

        var confirmButton = findViewById(R.id.confirmButton) as Button // Add this
        var confirmCode = findViewById(R.id.confirmCode) as EditText // Add this


        confirmButton.setOnClickListener { view ->
            var confirmCodeString = confirmCode.text.toString()
            var username = barangay_preference!!.getString("username", "")
            AWSMobileClient.getInstance().confirmSignUp(username, confirmCodeString,
                object : Callback<SignUpResult> /*Callback<SignUpResult!>()*/ {
                    override fun onResult(signUpResult: SignUpResult) {
                        runOnUiThread {
                            // Log.d(
                            //     FragmentActivity.TAG,
                            //     "Sign-up callback state: " + signUpResult.getConfirmationState()

                            // )


                            if (!signUpResult.getConfirmationState()) {
                                showError("Error in confirming")
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
                        showError("Error in confirming")
                    }
                }   )
        }

    }
}
