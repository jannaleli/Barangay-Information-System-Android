package com.example.barangayinformationsystem

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.text.SimpleDateFormat;
import java.util.*
import kotlinx.android.synthetic.main.activity_business_permit_application.*
import me.riddhimanadib.formmaster.FormBuilder
import me.riddhimanadib.formmaster.model.*
import java.util.UUID;
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
public interface generalCallback {
    fun onSuccess()
    fun onError(err: String)
}
class BusinessPermitApplication : AppCompatActivity() {

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
        setContentView(R.layout.activity_business_permit_application)
        var mRecyclerView = findViewById(R.id.permitRecyclerView) as RecyclerView
        var submitButton =  findViewById(R.id.submitPermit) as Button // Add this
        var    mFormBuilder = FormBuilder(this,mRecyclerView)



       // var mRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
       //  mFormBuilder = FormBuilder(this,mRecyclerView)

        val header = FormHeader.createInstance("Personal Instance")
        val businessname = FormElementTextSingleLine.createInstance().setTitle("Business Name").setHint("Enter Business Name").setTag(1)
        val ctcnumber = FormElementTextNumber.createInstance().setTitle("CTC Number").setValue("1000").setTag(2)

        val lessorsubdivision = FormElementTextSingleLine.createInstance().setTitle("Lessor Subdivision").setHint("").setTag(3)

        val lessorstreet = FormElementTextSingleLine.createInstance().setTitle("Lessor Street").setHint("").setTag(4)

        val businessactivity = FormElementTextSingleLine.createInstance().setTitle("Business Activity").setHint("Business Activity").setTag(5)

        val monthlyrental = FormElementTextNumber.createInstance().setTitle("Monthly Rental").setValue("1000").setTag(6)

        val lessorprovince = FormElementTextSingleLine.createInstance().setTitle("Lessor Province").setHint("Enter Lessor Province").setTag(7)

        val capitalization = FormElementTextNumber.createInstance().setTitle("Capitalization (if new)").setHint("Type in NA if not applicable").setTag(8)
        val grosssale = FormElementTextNumber.createInstance().setTitle("Gross Sale (if existing)").setHint("Type in NA if not applicable").setTag(9)
        val lessorbuildingnumber =FormElementTextNumber.createInstance().setTitle("Lessor Building Number").setHint("Enter Lessor's Building Number").setTag(10)
        val secno = FormElementTextNumber.createInstance().setTitle("SEC Number").setHint("Enter SEC Number").setTag(11)
        val lessoremailaddress = FormElementTextEmail.createInstance().setTitle("Lessor Email").setHint("Enter Email").setTag(12)
        val lessorbarangay = FormElementTextSingleLine.createInstance().setTitle("Lessor Barangay").setHint("Enter Lessor's Barangay").setTag(13)
        val numberofunits = FormElementTextSingleLine.createInstance().setTitle("No. of Units").setTag(14)
        val lessorname = FormElementTextSingleLine.createInstance().setTitle("Name").setHint("Enter Lessor Name").setTag(15)
        val lessorcity = FormElementTextSingleLine.createInstance().setTitle("City").setHint("Enter Lessor City").setTag(16)

        val businessstreet = FormElementTextSingleLine.createInstance().setTitle("Street").setHint("Enter Business Street").setTag(17)
        val businessbuildingnumber = FormElementTextNumber.createInstance().setTitle("Building Number").setHint("Enter Business Building Number").setTag(18)
        val businessActivityheader = FormHeader.createInstance("Business Details")
        val busiessPart = FormHeader.createInstance("Business Particulars")

        val lesserApplication = FormHeader.createInstance("Lessor Details (if Rented)")



        val formItems = ArrayList<Any>()
        formItems.add(busiessPart)
       // formItems.add(ctcnumber)
        formItems.add(secno)
        formItems.add(businessActivityheader)
        formItems.add(businessname)
        formItems.add(businessactivity)
        formItems.add(numberofunits)
        formItems.add(businessstreet)
        formItems.add(businessbuildingnumber)
        formItems.add(capitalization)
        formItems.add(grosssale)

        formItems.add(lesserApplication)
        formItems.add(lessorname)
        formItems.add(lessoremailaddress)
        formItems.add(lessorbuildingnumber)
        formItems.add(lessorcity)
        formItems.add(lessorsubdivision)
        formItems.add(lessorstreet)
        formItems.add(lessorbarangay)
        formItems.add(lessorprovince)
        formItems.add(monthlyrental)




        mFormBuilder.addFormElements(formItems as List<BaseFormElement>)

        submitButton.setOnClickListener {
                view ->
            barangay_preference = getSharedPreferences(PREFS_FILENAME, 0)
            var usernameString = barangay_preference!!.getString("username", null)
            val user_id = UUID.randomUUID().toString();
            val username = usernameString
            val businessname = mFormBuilder.getFormElement(1)
            val businessValue = businessname.getValue()
            val ctcnumber = mFormBuilder.getFormElement(2)
            val ctcValue = UUID.randomUUID().toString();
            val lessorsubdivision = mFormBuilder.getFormElement(3)
            val lessorsubdivisionvalue = lessorsubdivision.getValue()
            val lessorstreet = mFormBuilder.getFormElement(4)
            val lessorstreetvalue = lessorstreet.getValue()
            val businessactivity = mFormBuilder.getFormElement(5)
            val businessactivityv = businessactivity.getValue()
            val monthlyrental = mFormBuilder.getFormElement(6)
            val monthlyrentalv = monthlyrental.getValue()
            val lessorprovince = mFormBuilder.getFormElement(7)
            val lessorprovincev = lessorprovince.getValue()
            val capitalization = mFormBuilder.getFormElement(8)
            val capitalizationv = capitalization.getValue()
            val grosssale = mFormBuilder.getFormElement(9)
            val grosssalev = grosssale.getValue()
            val lessorbuildingnumber = mFormBuilder.getFormElement(10)
            val lessorbuildingnumberv = lessorbuildingnumber.getValue()
            val secno = mFormBuilder.getFormElement(11)
            val secnov = secno.getValue()
            val lessoremailaddress = mFormBuilder.getFormElement(12)
            val lessoremailaddressv = lessoremailaddress.getValue()
            val lessorbarangay = mFormBuilder.getFormElement(13)
            val lessorbarangayv = lessorbarangay.getValue()
            val numberofunits = mFormBuilder.getFormElement(14)
            val numberofunitsv = numberofunits.getValue()
            val lessorname = mFormBuilder.getFormElement(15)
            val lessornamev = lessorname.getValue()
            val lessorcity = mFormBuilder.getFormElement(16)
            val lessorcityv = lessorcity.getValue()
            val businessstreet = mFormBuilder.getFormElement(17)
            val businessstreetv = businessstreet.getValue()
            val businessbuildingnumber = mFormBuilder.getFormElement(18)
            val businessbuildingnumberv = businessbuildingnumber.getValue()

                var date = Date();
                val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val recent_date: String = formatter.format(date)



            val approvalDate = "Pending"


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
                        runOnUiThread {
                            showError("Application error. Please try again.")
                        }
                        println(err)
                    }
                }
                if( user_id == "" ||
                    username == "" ||
                    businessValue == "" ||
                    ctcValue == "" ||
                    lessorsubdivisionvalue == "" ||
                    lessorstreetvalue == "" ||
                    businessactivityv == "" ||
                    monthlyrentalv == "" ||
                    lessorprovincev == "" ||
                    capitalizationv == "" ||
                    grosssalev == "" ||
                    lessorbuildingnumberv == "" ||
                    secnov == "" ||
                    lessoremailaddressv == "" ||
                    lessorbarangayv == "" ||
                    numberofunitsv == "" ||
                    lessornamev == "" ||
                    lessorcityv == ""  ||
                    businessstreetv == ""  ||
                    businessbuildingnumberv == ""  ||
                    approvalDate == ""  ){

                    var msg = ""
                    if(user_id == ""){

                    }
                    if(username == ""){

                    }
                    if(businessValue == ""){
                        msg = msg + "Business\n"
                    }
                    if(ctcValue == ""){
                        msg = msg + "CTC \n"
                    }
                    if(lessorsubdivisionvalue == ""){
                        msg = msg + "Lessor Subdivision \n"
                    }
                    if(lessorstreetvalue == ""){
                        msg = msg + "Lessor Street \n"
                    }
                    if(businessactivityv == ""){
                        msg = msg + "Business Activity \n"
                    }
                    if(monthlyrentalv == ""){
                        msg = msg + "Monthly Rental \n"
                    }
                    if(lessorprovincev == ""){
                        msg = msg + "Lessor Province \n"
                    }
                    if(capitalizationv == ""){
                        msg = msg + "Capitalization \n"
                    }
                    if(grosssalev == ""){
                        msg = msg + "Gross Sale \n"
                    }
                    if(lessorbuildingnumberv == ""){
                        msg = msg + "Lessor Building Number \n"
                    }
                    if(secnov == ""){
                        msg = msg + "SEC No \n"
                    }
                    if(lessoremailaddressv == ""){
                        msg = msg + "Lessor Email Address \n"
                    }
                    if(lessorbarangayv == ""){
                        msg = msg + "Lessor Barangay \n"
                    }
                    if(numberofunitsv == ""){
                        msg = msg + "Number of Units \n"
                    }
                    if(lessornamev == ""){
                        msg = msg + "Lessor Name \n"
                    }
                    if(lessorcityv == ""){
                        msg = msg + "Lessor City \n"
                    }
                    if(businessstreetv == ""){
                        msg = msg + "Business Street \n"
                    }
                    if(businessbuildingnumberv == ""){
                        msg = msg + "Business Building Number \n"
                    }
                    if(approvalDate == ""){

                    }
                    runOnUiThread {
                        val message = "One of the fields are empty \n" + msg


                        showEmptyError(message)
                    }

                }else{
                    APICalls.sendPermit(
                        user_id,
                        username,
                        recent_date,
                         secnov,
                         businessbuildingnumberv,
                         businessstreetv,
                         businessactivityv,
                         businessValue,
                         capitalizationv,
                         ctcValue,
                         lessorbarangayv,
                         lessorbuildingnumberv,
                         lessorcityv,
                         lessoremailaddressv,
                         lessornamev,
                         lessorprovincev,
                         lessorstreetvalue,
                         lessorsubdivisionvalue,
                         monthlyrentalv,
                        "NEW",
                         grosssalev,
                        approvalDate,
                        callback
                    )
                }
                // Do network action in this function

            }).start()

        }

    }



}
