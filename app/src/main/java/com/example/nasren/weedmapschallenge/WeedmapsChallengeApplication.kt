package com.example.nasren.weedmapschallenge

import android.app.Application
import com.example.nasren.weedmapschallenge.api.YelpApiInterface

class WeedmapsChallengeApplication: Application() {

    private lateinit var yelpApiInterface: YelpApiInterface

    companion object{
        lateinit var appInstance: WeedmapsChallengeApplication

        fun getInstance(): WeedmapsChallengeApplication{
            return appInstance
        }

    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        yelpApiInterface = YelpApiInterface.create()
    }
}