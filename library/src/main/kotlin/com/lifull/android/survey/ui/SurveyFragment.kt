package com.lifull.android.survey.ui

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.lifull.android.survey.QuestionnaireManager
import com.lifull.android.survey.R
import com.lifull.android.survey.Survey
import com.lifull.android.survey.data.Response

/**
 * Questionnaire Top Page
 */
class SurveyFragment : Fragment(),
    OnClickListener {

    companion object {
        /** Const: Questionnaire Manager */
        private const val ARGS_QUESTIONNAIRE_MANAGER = "questionnaire_manager"

        /**
         * Get an instance of [SurveyFragment]
         *
         * @param questionnaireManager [QuestionnaireManager]
         * @return [SurveyFragment] instance
         */
        fun newInstance(questionnaireManager: QuestionnaireManager) = SurveyFragment()
            .apply {
                arguments = bundleOf(ARGS_QUESTIONNAIRE_MANAGER to questionnaireManager)
        }
    }


    /** [Survey] instance */
    private val survey: Survey by lazy {
        Survey.getInstance()
    }
    /** Questionnaire Manager */
    private val questionnaireManager: QuestionnaireManager by lazy {
        arguments!!.getParcelable<QuestionnaireManager>(ARGS_QUESTIONNAIRE_MANAGER)!!
    }

    /** Callback */
    private var callbacks: SurveyResultCallbacks? = null
    /** deep link */
    private var deepLink: String? = null

    /**
     * @inheritDoc
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        callbacks = context as? SurveyResultCallbacks
    }

    /**
     * @inheritDoc
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View.inflate(context, R.layout.fragment_survey, null)
    }

    /**
     * @inheritDoc
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        questionnaireManager.currentPage()?.let { fragment ->
            childFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, null)
                .commit()
        }
        deepLink = questionnaireManager.question.configure.deepLink
    }

    /**
     * @inheritDoc
     */
    override fun onDetach() {
        super.onDetach()

        callbacks = null
    }

    /**
     * @inheritDoc
     */
    override fun onClickNext(id: Int, responses: SparseArray<Response>) {
        survey.nextPage(responses)?.let { fragment ->
            childFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, null)
                .addToBackStack(null)
                .commit()
        }
    }

    /**
     * @inheritDoc
     */
    override fun onClickBack(id: Int) {
        survey.prevPageData()?.let {
            childFragmentManager.popBackStack()
        } ?: run {
            fragmentManager?.popBackStack()
        }
    }

    /**
     * @inheritDoc
     */
    override fun onClickSubmit(id: Int, responses: SparseArray<Response>) {
        survey.submit(responses)

        callbacks?.onResults(id, deepLink)
    }
}

/**
 * Callback Interface
 */
interface SurveyResultCallbacks {

    /**
     * Get questionnaire response results
     *
     * @param id Questionnaire ID
     * @param deepLink deep link
     */
    fun onResults(id: Int, deepLink: String? = null)
}