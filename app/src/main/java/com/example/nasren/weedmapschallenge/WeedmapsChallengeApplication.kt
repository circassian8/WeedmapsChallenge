package com.example.nasren.weedmapschallenge

import android.app.Application
import com.example.nasren.weedmapschallenge.api.YelpApiInterface

class WeedmapsChallengeApplication : Application() {
    companion object {
        var yelpApiInterface : YelpApiInterface = YelpApiInterface.create()
    }
}