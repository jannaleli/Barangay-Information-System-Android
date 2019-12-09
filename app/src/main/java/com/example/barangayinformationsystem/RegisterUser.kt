package com.example.barangayinformationsystem

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_register_user.*
import kotlinx.android.synthetic.main.content_login_user.*
import kotlinx.android.synthetic.main.content_register_user.*

class RegisterUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)
        setSupportActionBar(toolbar)
        submitRegisterButton.setOnClickListener {
                view ->

            val intent = Intent(this, BottomMenu::class.java)
            // start your next activity
            startActivity(intent)

        }

    }

}
