package com.example.barangayinformationsystem

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
//import com.amazonaws.mobile.client.AWSMobileClient

import kotlinx.android.synthetic.main.activity_launcher.*
import kotlinx.android.synthetic.main.content_launcher.*
import com.amazonaws.services.s3.AmazonS3Client
import android.app.Application
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class Launcher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        MobileAds.initialize(this, "ca-app-pub-7625476876021461~6950515027");

        loginButton.setOnClickListener {
            view ->
            barangay_preference = this.getSharedPreferences(PREFS_FILENAME, 0)
            var usernameString = barangay_preference!!.getString("username", null)

            if(usernameString == null) {
                val intent = Intent(this, LoginUser::class.java)
                // start your next activity
                startActivity(intent)
            }else{
                val intent = Intent(this, BottomMenu::class.java)
                // start your next activity
                startActivity(intent)
            }

            /*val intent = Intent(this, LoginUser::class.java)*/


        }




        registerButton.setOnClickListener {
                view ->

            val intent = Intent(this, RegisterResident::class.java)
            // start your next activity
            startActivity(intent)

        }

        tutorialButton.setOnClickListener {
                view ->

            val intent = Intent(this, OnboardingManager::class.java)
            // start your next activity
            startActivity(intent)

        }


    }

}
