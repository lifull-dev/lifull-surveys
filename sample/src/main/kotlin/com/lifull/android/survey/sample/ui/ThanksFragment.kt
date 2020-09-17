package com.lifull.android.survey.sample.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import com.lifull.android.survey.Survey
import com.lifull.android.survey.sample.R
import kotlinx.android.synthetic.main.fragment_thanks.*
import kotlinx.android.synthetic.main.fragment_thanks.view.*
import java.lang.StringBuilder

class ThanksFragment : Fragment() {

    companion object {

        private const val ARGS_DEEP_LINK = "deep_link"

        fun newInstance(deepLink: String? = null) = ThanksFragment().apply {
            arguments = Bundle(1).apply {
                putString(ARGS_DEEP_LINK, deepLink)
            }
        }
    }

    private val deepLink: String? by lazy {
        arguments!!.getString(ARGS_DEEP_LINK)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_thanks, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (deepLink.isNullOrEmpty()) {
            true -> {
                view.button.visibility = View.GONE
            }
            else -> {
                view.button.visibility = View.VISIBLE
            }
        }

        view.button.setOnClickListener {
            if (!deepLink.isNullOrEmpty()) {
                val uri = Uri.parse(deepLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }

        Survey.getInstance().getResponses()?.let { responses ->
            val builder = StringBuilder()
            responses.forEach { key, value ->
                builder.append("id:${key}, value:${value}")
            }
            view.textView.text = builder.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        button.setOnClickListener(null)
    }
}