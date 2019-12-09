package com.example.barangayinformationsystem

import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener

import kotlinx.android.synthetic.main.activity_onboarding_manager.*



class OnboardingManager : AppCompatActivity() {

    var fagmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_manager)

        var onboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding())
        var onboardingTransaction = fagmentManager.beginTransaction()
        onboardingTransaction.add(R.id.fragment_container, onboardingFragment)
        onboardingTransaction.commit()

        onboardingFragment.setOnRightOutListener(PaperOnboardingOnRightOutListener {

            var onboard: FragmentManager = supportFragmentManager

             fun onRightOut() {
                var fragmentTransaction = onboard.beginTransaction()
                 var bf = Onboarding()
                 fragmentTransaction.replace(R.id.fragment_container, bf)
                 onboardingTransaction.commit()
            }


        })




    }
    fun getDataForOnboarding(): ArrayList<PaperOnboardingPage> {

               var src1 = PaperOnboardingPage("Apply Documents", "Applying clearance and permit made even easier!",
            Color.parseColor("#678FB4"), R.drawable.document, R.drawable.document);
        var src2 = PaperOnboardingPage("Complaints Management", "Submit complaints anonymously!",
            Color.parseColor("#678FB4"), R.drawable.map, R.drawable.map);

        var src3 = PaperOnboardingPage("Events", "Be in the know for the next Barangay event such as free medical check up!",
            Color.parseColor("#678FB4"), R.drawable.calendar, R.drawable.calendar);

        var elements = ArrayList<PaperOnboardingPage>()
        elements.add(src1);
        elements.add(src2);
        elements.add(src3);
        return elements;
    }

}
