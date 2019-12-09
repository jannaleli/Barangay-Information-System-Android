package com.example.barangayinformationsystem

import android.content.DialogInterface

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.app.AlertDialog
import android.content.Context


public class Alert {
    public fun alert(message: String, context: Context) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        //set title for alert dialog
        builder.setTitle("Barangay e-Message")
        //set message for alert dialog
        builder.setMessage(message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("OK"){dialogInterface, which ->
            //Toast.makeText(applicationContext,"clicked yes",Toast.LENGTH_LONG).show()
        }
        //performing cancel action


        // Create the AlertDialog
        val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}