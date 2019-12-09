package com.example.barangayinformationsystem

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_post_complaint.*
import me.riddhimanadib.formmaster.FormBuilder
import me.riddhimanadib.formmaster.model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.R.attr.data
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat.getExtras
import java.util.*
import kotlin.collections.ArrayList


public interface mapCallback {
    fun onSuccess(latitude: String, longitude: String)
    fun onError(err: String)
}

public interface photoCallback {
    fun onSuccess(filename: String)
    fun onError(err: String)
}
class PostComplaint : AppCompatActivity() {
    var longitude: String = ""
    var latitude: String = ""
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
    var form: FormBuilder? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                latitude = data!!.getExtras()!!.getString("latitude")!!
                longitude = data!!.getExtras()!!.getString("longitude")!!
                fillInLocation()
            }
        }else{
            if (resultCode == Activity.RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                val name = data!!.getExtras()!!.getString("filename")
                val file = form!!.getFormElement(4)
                file.setValue(name)


            }
        }



    }

    fun fillInLocation(){
        var longitudeText = findViewById(R.id.longitudeText) as TextView
        var latitudeText = findViewById(R.id.latitudeText) as TextView
        longitudeText.text = longitude
        latitudeText.text = latitude

    }

    fun createForm() {
        var mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
        var mFormBuilder = FormBuilder(this, mRecyclerView)
        var submitButton = findViewById(R.id.submitComplaint) as Button // Add this
        var getLocButton = findViewById(R.id.getLoc) as Button // Add this
       // var photoButton: Button = findViewById(R.id.pickPhoto) as Button
        val header = FormHeader.createInstance("Personal Instance")
        val text = FormElementTextSingleLine.createInstance().setTitle("Complaint Title")
            .setHint("Enter Complaint Summary").setTag(1)
        val description =
            FormElementTextMultiLine.createInstance().setTitle("Complaint Description").setTag(2)

        val date =
            FormElementPickerDate.createInstance().setTitle("Date").setDateFormat("MMM dd, yyyy")
                .setTag(3)
        //val longitudeForm =
        //    FormElementTextSingleLine.createInstance().setTitle("Latitude").setHint("Latitude")
        //        .setTag(4).setValue(latitude)
        //val latitudeForm =
        //    FormElementTextSingleLine.createInstance().setTitle("Longitude").setHint("Longitude")
         //       .setTag(5).setValue(longitude)
        var username = barangay_preference!!.getString("username", "")
        form = mFormBuilder

        val status = ArrayList<String>()
        status.add("Roads & Footpaths")
        status.add("Public Facilities")
        status.add("Pests")
        status.add("Stray Animals")
        status.add("Drinking Water")
        status.add("Drains & Sewers")
        status.add("Parks & Greenery")
        status.add("Public Dispute")
        status.add("Unecessary Noise")
        status.add("Others")
        val complaintCat = FormElementPickerSingle.createInstance().setTitle("Complaint Category")
            .setOptions(status).setPickerTitle("Pick any item").setTag(7)
        val userstatus = ArrayList<String>()
        userstatus.add("Anonymous")
        userstatus.add(username!!)
        val userNow = FormElementPickerSingle.createInstance().setTitle("Identity").setOptions(userstatus).setPickerTitle("Anonymous or reveal yourself?").setTag(8)


        val formItems = ArrayList<Any>()
        formItems.add(header)
       // formItems.add(text)
        formItems.add(description)
        formItems.add(date)
        formItems.add(complaintCat)
        //formItems.add(longitudeForm)
        //formItems.add(latitudeForm)
        formItems.add(userNow)
       // formItems.add("null")

        mFormBuilder.addFormElements(formItems as List<BaseFormElement>)
/*
        photoButton.setOnClickListener { view ->
            val intent = Intent(this, PhotoPicker::class.java)
            // start your next activity
            startActivityForResult(intent, 1);

        }

   */     getLocButton.setOnClickListener { view ->


            val intent = Intent(this, GetLocation::class.java)
            // start your next activity


            startActivityForResult(intent, 0);
        }

        submitButton.setOnClickListener { view ->

            Thread(Runnable {
                // Do network action in this function
                barangay_preference = getSharedPreferences(PREFS_FILENAME, 0)

              //  val text = mFormBuilder.getFormElement(1)
              //  val textv = text.getValue()

                val description = mFormBuilder.getFormElement(2)
                val descriptionv = description.getValue()

                val date = mFormBuilder.getFormElement(3)
                val datev = date.getValue()

                val complaintCat = mFormBuilder.getFormElement(7)
                val complaintCatv = complaintCat.getValue()

                val identity = mFormBuilder.getFormElement(8)
                val identityvalue = identity.getValue()

                val complaintId = UUID.randomUUID().toString();
               // val proof = mFormBuilder.getFormElement(6)
                //val proofv = proof.getValue()
              //  val attachmentId = proofv

                val user_id = identityvalue
                var answer: String = ""
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                    answer = current.format(formatter)
                    Log.d("answer", answer)
                } else {

                }
                val createDate = answer

                val callback = object : generalCallback {
                    override fun onSuccess() {
                        // no errors
                        //display success message
                        runOnUiThread {
                            showSuccess("Complaint has been posted!")
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
                if( user_id == "" ||
                    complaintId == "" ||
                    descriptionv == "" ||
                    latitude == "" ||
                    longitude == "" ||
                    complaintCatv == "" ||
                    user_id == "" ||
                     datev == "" ){

                    runOnUiThread {
                        showEmptyError("One of the fields are empty")
                    }

                }else{
                    APICalls.postComplaint(
                        complaintId,
                        "sample",
                        descriptionv,
                        latitude,
                        longitude,
                        complaintCatv,
                        user_id,
                        "New",
                        datev,
                        callback


                    )
                }


            }).start()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_complaint)
        createForm()

        }
    }



