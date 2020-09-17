package com.lifull.android.survey.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.lifull.android.survey.Survey
import com.lifull.android.survey.sample.R
import com.lifull.android.survey.sample.ui.OnClickListener
import com.lifull.android.survey.ui.SurveyFragment
import com.lifull.android.survey.ui.SurveyResultCallbacks

class MainActivity : AppCompatActivity(),
    SurveyResultCallbacks, OnClickListener {

    private val survey: Survey by lazy {
        Survey.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ListFragment.newInstance().let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, it, null).commit()
        }
    }

    override fun onClickQuestionnaire(id: Int) {
        survey.start(id)?.let {
            val fragment = SurveyFragment.newInstance(it)
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, null).addToBackStack(null).commit()
        }
    }

    override fun onResults(id: Int, deepLink: String?) {
        val fragment = ThanksFragment.newInstance(deepLink)
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, null)
            .addToBackStack(null)
            .commit()
    }
}