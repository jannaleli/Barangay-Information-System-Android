package com.example.barangayinformationsystem

import android.graphics.Typeface
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bottom_menu.*
import kotlinx.android.synthetic.main.activity_check_status.*
import android.app.Application
import com.google.android.gms.ads.*


class BottomMenu : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var mAdView: AdView? = null

    private var manager = supportFragmentManager
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                createEventFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {

                createDocumentFrag()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                createComplaintFragment()
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_emergency -> {
                createEmergencyFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_menu)

        setSupportActionBar(toolbar2)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        mAdView = findViewById(R.id.adView)
        //mAdView!!.adSize = AdSize.BANNER
      //  mAdView!!.adUnitId = "ca-app-pub-7625476876021461/6286787151"
        var adRequest: AdRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest!!)


        mAdView!!.setAdListener(object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        })



        createEventFragment()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    fun createEventFragment() {

        val transaction = manager.beginTransaction()
        var eventFrag = MyQuoteListFragment()

        transaction.replace(R.id.fragmentHolder, eventFrag)
        transaction.commit()

    }

    fun createEmergencyFragment() {

        val transaction = manager.beginTransaction()
        var emergFrag = PhoneNumber()

        transaction.replace(R.id.fragmentHolder, emergFrag)
        transaction.commit()

    }

    fun createComplaintFragment() {
        val transaction = manager.beginTransaction()
        var complaintFrag = ComplaintFragment()

        transaction.replace(R.id.fragmentHolder, complaintFrag)
        transaction.commit()

    }
    fun createDocumentFrag() {
        val transaction = manager.beginTransaction()
        var documentFrag = DocumentFragment()

        transaction.replace(R.id.fragmentHolder, documentFrag)
        transaction.commit()

    }

    fun Toolbar.changeToolbarFont(){
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = Typeface.createFromAsset(view.context.assets, "fonts/customFont")
                break
            }
        }
    }
}


