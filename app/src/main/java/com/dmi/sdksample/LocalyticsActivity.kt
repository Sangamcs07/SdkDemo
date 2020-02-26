package com.dmi.sdksample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class LocalyticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localytics)
        System.currentTimeMillis()
        startLt()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("Inida")
        //Localytics.onNewIntent(this, intent);
    }

    private fun startLt() {
        //Localytics.autoIntegrate(application)
    }

    fun hitLL(view: View) {
        //Localytics.tagEvent("SangTest")

        val attributes: MutableMap<String, String> = HashMap()
        attributes["Team Name"] = "CSK"
        attributes["City"] = "India"

        //Localytics.tagEvent("SangTestAttrib", attributes)
    }
}
