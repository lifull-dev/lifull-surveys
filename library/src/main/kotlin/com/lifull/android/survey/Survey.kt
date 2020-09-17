package com.lifull.android.survey

import android.util.SparseArray
import androidx.core.util.valueIterator
import com.lifull.android.survey.data.PageData
import com.lifull.android.survey.data.Response
import com.lifull.android.survey.data.local.Questionnaire
import com.lifull.android.survey.repository.Repository
import com.lifull.android.survey.ui.QuestionFragment

/**
 * Survey
 */
class Survey private constructor() {
    companion object {
        /** Singleton Instance */
        private var instance: Survey? = null

        /**
         * Create an instance of [Survey]
         *
         * @return [Survey] instance
         */
        fun getInstance(): Survey {
            if (instance == null) {
                instance = Survey()
            }
            return instance!!
        }

        /**
         * 初期化処理
         *
         * @param repository [Repository]
         */
        fun init(repository: Repository) {
            getInstance().repository = repository
        }
    }

    /** [QuestionnaireManager] */
    private var questionnaireManager: QuestionnaireManager? = null
    /** [Repository] */
    lateinit var repository: Repository

    /**
     * Start the specified survey
     *
     * @param id ID
     * @return [QuestionnaireManager]
     */
    fun start(id: Int): QuestionnaireManager? {
        val questionnaire = repository.getQuestionnaire(id)
        questionnaireManager = if (questionnaire == null) {
            null
        } else {
            QuestionnaireManager(questionnaire)
        }
        return questionnaireManager
    }

    /**
     * Get a prev page data
     *
     * @return [PageData] or null
     */
    fun prevPageData(): PageData? {
        val manager = questionnaireManager ?: return null
        return manager.back()
    }

    /**
     * Get a next questionnaire page
     *
     * @param responses response
     * @return [QuestionFragment] or null
     */
    fun nextPage(responses: SparseArray<Response>): QuestionFragment? {
        val manager = questionnaireManager ?: return null
        val page = responses.valueIterator().forEach {
            manager.put(it)
        }.let {
            manager.next()
        } ?: return null
        return manager.getPageFragment(page)
    }

    /**
     * Complete the survey
     *
     * @param responses Response
     */
    fun submit(responses: SparseArray<Response>) {
        val manager = questionnaireManager ?: return
        // save the response
        responses.valueIterator().forEach {
            manager.put(it)
        }
        manager.complete()
    }

    /**
     * Get an list of Response
     *
     * @return Response
     */
    fun getResponses(): SparseArray<Response>? {
        val manager = questionnaireManager ?: return null
        return manager.getResponses()
    }

    /**
     * Get an list of questionnaire
     *
     * @return list of questionnaire
     */
    fun getQuestionnaires(): List<Questionnaire> {
        return repository.getQuestionnaires()
    }
}