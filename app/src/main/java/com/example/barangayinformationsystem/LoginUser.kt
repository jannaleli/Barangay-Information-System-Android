package com.example.barangayinformationsystem

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_login_user.*
import kotlinx.android.synthetic.main.content_launcher.*
import kotlinx.android.synthetic.main.content_login_user.*
import kotlinx.android.synthetic.main.content_register_user.*
import me.riddhimanadib.formmaster.FormBuilder
import me.riddhimanadib.formmaster.model.*
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoMfaSettings.SMS_MFA
import com.amazonaws.mobile.client.results.SignInResult
import android.R.attr.password
import android.content.SharedPreferences
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.amazonaws.mobile.auth.ui.SignInUI
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider.AttributeKeys.BACKGROUND_COLOR
import com.amazonaws.mobile.client.results.SignInState
import com.amazonaws.mobile.client.UserStateDetails
import com.rengwuxian.materialedittext.MaterialEditText

val PREFS_FILENAME = "com.barangaylomadegato.pref"
var barangay_preference: SharedPreferences? = null
public interface residentCallback {
    fun onSuccess(resident: ResidentModel)
    fun onError(err: String)
}
class LoginUser : AppCompatActivity() {

    fun showError() {

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Failure")
        //set message for alert dialog
        builder.setMessage("Login Not Successful")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("OK"){dialogInterface, which ->
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
        }
        //performing cancel action


        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun showSuccess() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Failure")
        //set message for alert dialog
        builder.setMessage("Login Not Successful")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("OK"){dialogInterface, which ->
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
        }
        //performing cancel action


        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)
        var submit = findViewById<Button>(R.id.submitLoginButton)
        var username = findViewById<TextView>(R.id.loginUsername) as MaterialEditText
        var password = findViewById<TextView>(R.id.loginPassword) as MaterialEditText


        submit.setOnClickListener {
                view ->


            val intent = Intent(this, BottomMenu::class.java)
            // start your next activity


            Thread(Runnable {
                val callback = object : residentCallback {
                    override fun onSuccess(resident: ResidentModel) {
                        // no errors
                        //display success message

                        startActivity(intent)
                    }



                    override fun onError(err: String) {
                        // error happen
                        println(err)
                    }
                }


                val editor = barangay_preference!!.edit()
                editor.putString("username", username.text.toString())
                editor.commit()
                AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {

                    override fun onResult(userStateDetails: UserStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.userState)
                        AWSMobileClient.getInstance().signIn(username.text.toString(), password.text.toString(), null, object : Callback<SignInResult> {
                            override fun onResult(signInResult: SignInResult) {
                                runOnUiThread {
                                    //  Log.d(FragmentActivity.TAG, "Sign-in callback state: " + signInResult.signInState)
                                    when (signInResult.signInState) {
                                        SignInState.DONE ->  startActivity(intent)
                                        SignInState.SMS_MFA -> Log.d("tag","Please confirm sign-in with SMS.")
                                        SignInState.NEW_PASSWORD_REQUIRED ->  Log.d("tag","Please confirm sign-in with new password.")
                                        else ->Log.d("tag","Unsupported sign-in confirmation: " + signInResult.signInState)
                                    }
                                }
                            }

                            override fun onError(e: Exception) {
                                Log.e("error", "Sign-in error", e)
                                runOnUiThread({
                                    showError()
                                })

                            }
                        })

                    }

                    override fun onError(e: Exception) {
                        Log.e("INIT", "Initialization error.", e)
                        runOnUiThread({
                          showError()
                        })
                    }
                }
                )






            }).start()

        }


    }

}
