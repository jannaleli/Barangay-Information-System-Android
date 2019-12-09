package com.example.barangayinformationsystem

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_clearance_application.*
import me.riddhimanadib.formmaster.FormBuilder
import me.riddhimanadib.formmaster.model.*
import java.util.UUID;
class ClearanceApplication : AppCompatActivity() {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clearance_application)

        var mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
        var submitButton =  findViewById(R.id.submitClearance) as Button // Add this
        var mFormBuilder = FormBuilder(this,mRecyclerView)



        val header = FormHeader.createInstance("Personal Instance")
       // val reason = FormElementTextSingleLine.createInstance().setTitle("Reason").setHint("Enter Business Name")
        val governmentid = FormElementTextNumber.createInstance().setTitle("Government ID (Passport/TIN/SSS/Etc.)").setTag(1)



        val reason = ArrayList<String>()
        reason.add("Employment")
        reason.add("Business Permit")

        val reasonStatus = FormElementPickerSingle.createInstance().setTitle("Reason for Application").setOptions(reason).setPickerTitle("Pick any item").setTag(2);





        val formItems = ArrayList<Any>()
        formItems.add(header)
        formItems.add(reasonStatus)
        formItems.add(governmentid)



        mFormBuilder.addFormElements(formItems as List<BaseFormElement>)
        barangay_preference = getSharedPreferences(PREFS_FILENAME, 0)
        var username = barangay_preference!!.getString("username", "")
        submitButton.setOnClickListener {
                view ->

            val user_id = UUID.randomUUID().toString();
            val usernameNow = username
            val reason = mFormBuilder.getFormElement(2)
            val reasonValue = reason.getValue()
            val governmentId = mFormBuilder.getFormElement(1)
            val governmentIdValue = governmentId.getValue()



            Thread(Runnable {

                val callback = object : generalCallback {
                    override fun onSuccess() {
                        // no errors
                        //display success message
                        runOnUiThread {
                            showSuccess("Successful application. Please head on over to Status page to check for your status. Application ID:" + user_id)
                        }
                    }



                    override fun onError(err: String) {
                        // error happen
                        println(err)
                        runOnUiThread {
                            showError("Application error. Please try again.")
                        }
                    }
                }
                // Do network action in this function

                if( user_id == "" ||
                    usernameNow == "" ||
                    governmentIdValue == "" ||
                    reasonValue == "" ){

                    runOnUiThread {
                        showEmptyError("One of the fields are empty")
                    }

                }else{
                    APICalls.sendDocuments(
                        user_id,
                        usernameNow,
                        "null",
                        governmentIdValue,
                        "none",
                        reasonValue,
                        "NEW",
                        callback
                    )
                }

            }).start()

        }
    }


    }


