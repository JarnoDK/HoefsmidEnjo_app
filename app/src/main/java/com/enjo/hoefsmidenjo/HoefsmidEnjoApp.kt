package com.enjo.hoefsmidenjo

import android.app.Application
import timber.log.Timber

class HoefsmidEnjoApp: Application() {
    override fun onCreate(){
        Timber.plant(Timber.DebugTree())
    }
}


