package com.lifull.android.survey.sample

import android.app.Application
import com.lifull.android.survey.Survey
import com.lifull.android.survey.sample.repository.MockRepository

class MyApplication() : Application() {

    override fun onCreate() {
        super.onCreate()

        Survey.init(MockRepository())
    }
}