package com.lifull.android.survey.sample.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lifull.android.survey.Survey
import com.lifull.android.survey.sample.R
import com.lifull.android.survey.sample.ui.OnClickListener
import kotlinx.android.synthetic.main.fragment_questionnaire.*

class ListFragment : Fragment() {
    companion object {

        fun newInstance(): ListFragment = ListFragment()
    }

    private var callback: OnClickListener? = null

    private val survey: Survey by lazy {
        Survey.getInstance()
    }

    private val adapter: ListAdapter by lazy {
        ListAdapter(mutableListOf()) { id ->
            callback?.onClickQuestionnaire(id)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = context as OnClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_questionnaire, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (adapter.items.isEmpty()) {
            survey.getQuestionnaires().forEach {
                adapter.items.add(ListItem(it.id, it.name, it.createdAt, it.expiredAt))
            }
        }
        recycler_view.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recycler_view.adapter = null
    }

    override fun onDetach() {
        super.onDetach()

        callback = null
    }
}

interface OnClickListener {

    fun onClickQuestionnaire(id: Int)
}