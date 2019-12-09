package com.example.barangayinformationsystem

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView

import me.riddhimanadib.formmaster.FormBuilder
import me.riddhimanadib.formmaster.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails
import android.R.attr.password
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
//import javax.swing.UIManager.put
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.*
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.amazonaws.mobile.client.results.SignUpResult
import com.amazonaws.mobile.config.AWSConfiguration
import java.util.*
import kotlin.collections.HashMap


class RegisterResident : AppCompatActivity() {

    fun showError(message: String) {

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


        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    fun showSuccess(message: String) {

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Success")
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("OK"){dialogInterface, which ->
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
            finish()
        }
        //performing cancel action


        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    fun showEmptyError(message: String) {

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Failure")
        //set message for alert dialog
        builder.setMessage(message)
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
        setContentView(R.layout.activity_register_resident)
        var mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
        var mFormBuilder = FormBuilder(this,mRecyclerView)
        var submitButton =  findViewById(R.id.registerResident) as Button // Add this


        val header = FormHeader.createInstance("Personal Instance")
        val username = FormElementTextSingleLine.createInstance().setTitle("Username").setHint("Enter Username").setTag(1)
        val password =    FormElementTextPassword.createInstance().setTitle("Password (Upper, Lower, Numerical, Symbol)").setTag(2)
        val firstname = FormElementTextSingleLine.createInstance().setTitle("First Name").setHint("Enter First Name").setTag(3)

        val lastname = FormElementTextSingleLine.createInstance().setTitle("Last Name").setHint("Enter Last Name").setTag(4)

        val emailaddress = FormElementTextEmail.createInstance().setTitle("Email").setHint("Enter Email").setTag(5)

        val phonenumber = FormElementTextPhone.createInstance().setTitle("Phone").setValue("+63").setTag(6)

        val birthdate = FormElementPickerDate.createInstance().setTitle("Birth Date").setDateFormat("MMM dd, yyyy").setTag(7)

        val birthplace =FormElementTextSingleLine.createInstance().setTitle("Birthplace").setHint("Enter Birthplace").setTag(8)



        val genderStatus = ArrayList<String>()
        genderStatus.add("Female")
        genderStatus.add("Male")
        val gender = FormElementPickerSingle.createInstance().setTitle("Gender").setOptions(genderStatus).setPickerTitle("Gender").setTag(9).setHint("Click to choose Gender")

        val status = ArrayList<String>()
        status.add("Single")
        status.add("Married")
        status.add("Widowed")
        val civilstatus = FormElementPickerSingle.createInstance().setTitle("Civil Status").setOptions(status).setPickerTitle("Civil Status").setTag(10).setHint("Click to choose Civil Status")

// multiple items picker input


        val tinnumber = FormElementTextNumber.createInstance().setTitle("TIN").setHint("Enter TIN").setTag(12)

        val profession = FormElementTextSingleLine.createInstance().setTitle("Profession").setHint("Enter Profession").setTag(13)
        val weight = FormElementTextNumber.createInstance().setTitle("Weight").setHint("Enter Weight (Kilogram)").setTag(14)

        val height = FormElementTextNumber.createInstance().setTitle("Height").setHint("Enter Height (Inches)").setTag(15)
        val address = FormElementTextSingleLine.createInstance().setTitle("Address").setHint("Enter Address").setTag(16)
        val control  = FormElementTextNumber.createInstance().setTitle("Control Number").setHint("Enter Control Number").setTag(17)

        val zipno  = FormElementTextNumber.createInstance().setTitle("Zip Number").setHint("Enter Zip Number").setTag(18)
        val gross_income  = FormElementTextNumber.createInstance().setTitle("Gross Income").setHint("Enter Gross Income").setTag(19)


        val formItems = ArrayList<Any>()
        formItems.add(header)
        formItems.add(emailaddress)
       // formItems.add(username)
        formItems.add(password)
        formItems.add(firstname)
        formItems.add(lastname)

        formItems.add(phonenumber)
        formItems.add(birthdate)
        formItems.add(birthplace)
        formItems.add(gender)
        formItems.add(civilstatus)
        formItems.add(address)
        formItems.add(zipno)
        formItems.add(gross_income)
        //formItems.add(control)

        formItems.add(tinnumber)
        formItems.add(profession)
        formItems.add(weight)
        formItems.add(height)
        mFormBuilder.addFormElements(formItems as List<BaseFormElement>)

        submitButton.setOnClickListener {
                view ->

           // val username = mFormBuilder.getFormElement(1)
           // val usernamev = username.getValue()

            val password = mFormBuilder.getFormElement(2)
            val passwordv = password.getValue()

            val firstname = mFormBuilder.getFormElement(3)
            val firstnamev = firstname.getValue()

            val lastname = mFormBuilder.getFormElement(4)
            val lastnamev = lastname.getValue()

            val emailaddress = mFormBuilder.getFormElement(5)
            val emailaddressv = emailaddress.getValue()

            val phonenumber = mFormBuilder.getFormElement(6)
            val phonenumberv = phonenumber.getValue()

            val birthdate = mFormBuilder.getFormElement(7)
            val birthdatev = birthdate.getValue()

            val birthplace = mFormBuilder.getFormElement(8)
            val birthplacev = birthplace.getValue()

            val gender = mFormBuilder.getFormElement(9)
            val genderv = gender.getValue()

            val civilstatus = mFormBuilder.getFormElement(10)
            val civilstatusv = civilstatus.getValue()



            val tinnumber = mFormBuilder.getFormElement(12)
            val tinnumberv = tinnumber.getValue()

            val profession = mFormBuilder.getFormElement(13)
            val professionv = profession.getValue()

            val weight = mFormBuilder.getFormElement(14)
            val weightv = weight.getValue()

            val height = mFormBuilder.getFormElement(15)
            val heightv = height.getValue()



            val address = mFormBuilder.getFormElement(16)
            val addressv = address.getValue()

          //  val control = mFormBuilder.getFormElement(17)
          //  val controlv = control.getValue()

            val zipno = mFormBuilder.getFormElement(18)
            val zipnov = zipno.getValue()

            val gross_income = mFormBuilder.getFormElement(19)
            val gross_incomev = gross_income.getValue()

            val user_id = emailaddress.getValue()
            var answer: String = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                answer =  current.format(formatter)
                Log.d("answer",answer)
            } else {

            }
            val createdatev = answer
            Thread(Runnable {
                // Do network action in this function



                val callback = object : generalCallback {
                    override fun onSuccess() {
                        // no errors
                        //display success message
                        val attributes = HashMap<String, String>()
                        attributes.put("email", emailaddressv)
                        AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {

                            override fun onResult(userStateDetails: UserStateDetails) {
                                Log.i("INIT", "onResult: " + userStateDetails.userState)
                                AWSMobileClient.getInstance()
                                    .signUp(emailaddressv, passwordv, attributes, null,
                                        object : Callback<SignUpResult> /*Callback<SignUpResult!>()*/ {
                                            override fun onResult(signUpResult: SignUpResult) {
                                                runOnUiThread {
                                                    // Log.d(
                                                    //     FragmentActivity.TAG,
                                                    //     "Sign-up callback state: " + signUpResult.getConfirmationState()
                                                    // )
                                                    if (!signUpResult.getConfirmationState()) {
                                                        val details = signUpResult.getUserCodeDeliveryDetails()
                                                        //  makeToast("Confirm sign-up with: " + details.getDestination())
                                                      //  val intent = Intent(
                                                       //     this@RegisterResident,
                                                        //    ConfirmSignUp::class.java
                                                       // )    //(this, SignInActivity::class.java)
                                                        // start your next activity
                                                        //startActivity(intent)
                                                        runOnUiThread {
                                                            showSuccess("An administrator will confirm your sign up!")
                                                        }
                                                    } else {
                                                        // makeToast("Sign-up done.")
                                                        runOnUiThread {
                                                            showSuccess("Successful Sign up!")
                                                        }
                                                    }
                                                }
                                            }

                                            override fun onError(e: Exception) {
                                                   Log.e("tag", "Sign-up error", e)
                                                runOnUiThread {
                                                 showError("Sign-up error")
                                                }
                                            }
                                        })

                            }

                            override fun onError(e: Exception) {
                                runOnUiThread {
                                    Log.e("INIT", "Initialization error.", e)
                                    runOnUiThread {
                                        showError("Initialization error.")
                                    }
                                }
                            }
                        })

                    }



                    override fun onError(err: String) {
                        // error happen
                        println(err)
                        runOnUiThread {
                            showError("Sign-up error")
                        }
                    }
                }
                if( user_id == "" ||
                    emailaddressv == "" ||
                    passwordv == "" ||
                    addressv == "" ||
                    birthdatev == "" ||
                    createdatev == "" ||
                    firstnamev == "" ||
                    lastnamev == "" ||
                    phonenumberv == "" ||
                    zipnov == "" ||
                    civilstatusv == "" ||
                    tinnumberv == "" ||
                    birthplacev == "" ||
                    weightv == "" ||
                    heightv == "" ||
                    professionv == "" ||
                    genderv == ""   ){

                    runOnUiThread {
                        showEmptyError("One of the fields are empty")
                    }

                }else{
                    APICalls.registerResident( user_id, emailaddressv ,  passwordv,  addressv,  birthdatev,
                        createdatev,  firstnamev,  lastnamev,  phonenumberv,  zipnov,
                        civilstatusv,  UUID.randomUUID().toString(),  tinnumberv,  birthplacev,  weightv,
                        heightv, UUID.randomUUID().toString() ,  professionv,  gross_incomev,  "blank slate",
                        genderv,  "NEW", callback)

                }



//                val attributes = HashMap<String, String>()
//                attributes.put("email",usernamev)
//                AWSMobileClient.getInstance().initialize(applicationContext, object : Callback<UserStateDetails> {
//
//                    override fun onResult(userStateDetails: UserStateDetails) {
//                        Log.i("INIT", "onResult: " + userStateDetails.userState)
//                        AWSMobileClient.getInstance()
//                            .signUp(usernamev, passwordv, attributes, null,
//                                object : Callback<SignUpResult> /*Callback<SignUpResult!>()*/ {
//                                    override fun onResult(signUpResult: SignUpResult) {
//                                        runOnUiThread {
//                                            // Log.d(
//                                            //     FragmentActivity.TAG,
//                                            //     "Sign-up callback state: " + signUpResult.getConfirmationState()
//                                            // )
//                                            if (!signUpResult.getConfirmationState()) {
//                                                val details = signUpResult.getUserCodeDeliveryDetails()
//                                                //  makeToast("Confirm sign-up with: " + details.getDestination())
//                                                val intent = Intent(
//                                                    this@RegisterResident,
//                                                    ConfirmSignUp::class.java
//                                                )    //(this, SignInActivity::class.java)
//                                                // start your next activity
//                                                startActivity(intent)
//                                            } else {
//                                                // makeToast("Sign-up done.")
//                                            }
//                                        }
//                                    }
//
//                                    override fun onError(e: Exception) {
//                                           Log.e("INIT", "Sign-up error", e)
//                                    }
//                                })
//
//                    }
//
//                    override fun onError(e: Exception) {
//                        Log.e("INIT", "Initialization error.", e)
//                    }
//                })
        }).start()


    }

    fun getVal(){
//        val username = mFormBuilder.getFormElement(1)
//        val usernamev = username.getValue()
//
//        val password = mFormBuilder.getFormElement(2)
//        val passwordv = password.getValue()
//
//        val firstname = mFormBuilder.getFormElement(3)
//        val firstnamev = firstname.getValue()
//
//        val lastname = mFormBuilder.getFormElement(4)
//        val lastnamev = lastname.getValue()
//
//        val emailaddress = mFormBuilder.getFormElement(5)
//        val emailaddressv = emailaddress.getValue()
//
//        val phonenumber = mFormBuilder.getFormElement(6)
//        val phonenumberv = phonenumber.getValue()
//
//        val birthdate = mFormBuilder.getFormElement(7)
//        val birthdatev = birthdate.getValue()
//
//        val birthplace = mFormBuilder.getFormElement(8)
//        val birthplacev = birthplace.getValue()
//
//        val gender = mFormBuilder.getFormElement(9)
//        val genderv = gender.getValue()
//
//        val civilstatus = mFormBuilder.getFormElement(10)
//        val civilstatusv = civilstatus.getValue()
//
//        val ctcnumber = mFormBuilder.getFormElement(11)
//        val ctcnumberv = ctcnumber.getValue()
//
//        val tinnumber = mFormBuilder.getFormElement(12)
//        val tinnumberv = tinnumber.getValue()
//
//        val profession = mFormBuilder.getFormElement(13)
//        val professionv = profession.getValue()
//
//        val weight = mFormBuilder.getFormElement(14)
//        val weightv = weight.getValue()
//
//        val height = mFormBuilder.getFormElement(15)
//        val heightv = height.getValue()
    }

}}
