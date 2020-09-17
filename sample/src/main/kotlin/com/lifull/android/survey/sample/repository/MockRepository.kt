package com.lifull.android.survey.sample.repository

import com.lifull.android.survey.QuestionType
import com.lifull.android.survey.data.CheckBoxItem
import com.lifull.android.survey.data.local.*
import com.lifull.android.survey.repository.Repository

class MockRepository: Repository {

    override fun getQuestionnaire(id: Int): Questionnaire? {
        return getQuestionnaires().find { it.id == id }
    }

    override fun getQuestionnaires(): List<Questionnaire> {
        return listOf(
            singleAnswerSample(),
            multipleAnswersSample(),
            openEndedSample(),
            deepLinkAnswerSample(),
            multiPageSample(),
            mixedAnswerSample())
    }

    private fun singleAnswerSample(): Questionnaire {
        val questionItem = QuestionItem(
            0,
            QuestionType.SINGLE_ANSWER.id,
            "Single Answer",
            listOf(
                ResponseItem(0, "Response A"),
                ResponseItem(1, "Response B"),
                ResponseItem(2, "Response C"),
                ResponseItem(3, "Response D")
            )
        )
        val page = PageItem(
            0,
            listOf(questionItem)
        )
        return Questionnaire(
            1,
            "Sample of SingleAnswer",
            listOf(page)
        )
    }

    private fun multipleAnswersSample(): Questionnaire {
        val questionItem = QuestionItem(
            0,
            QuestionType.MULTIPLE_ANSWERS.id,
            "Multiple Answers",
            listOf(
                ResponseItem(1, "Response A"),
                ResponseItem(2, "Response B"),
                ResponseItem(3, "Response C"),
                ResponseItem(4, "Response D"),
                ResponseItem(CheckBoxItem.OTHER_RESPONSE_ID, "other")
            )
        )
        val page = PageItem(
            0,
            listOf(questionItem)
        )
        return Questionnaire(
            2,
            "Sample of Multiple Answer",
            listOf(page)
        )
    }

    private fun openEndedSample(): Questionnaire {
        val questionItem = QuestionItem(
            0,
            QuestionType.OPEN_ENDED.id,
            "Open Ended",
            listOf(ResponseItem(0, ""))
        )
        val page = PageItem(
            0,
            listOf(questionItem)
        )
        return Questionnaire(
            3,
            "Sample of Open Ended",
            listOf(page)
        )
    }

    private fun deepLinkAnswerSample(): Questionnaire {
        val configure = QuestionnaireConfigure(
            submit = "Check results",
            deepLink = "http://www.homes.co.jp"
        )
        val questionItem = QuestionItem(
            0,
            QuestionType.SINGLE_ANSWER.id,
            "Single Answer",
            listOf(
                ResponseItem(0, "Response A"),
                ResponseItem(1, "Response B"),
                ResponseItem(2, "Response C"),
                ResponseItem(3, "Response D")
            )
        )
        val page = PageItem(
            0,
            listOf(questionItem)
        )
        return Questionnaire(
            4,
            "Sample of DeepLink",
            listOf(page),
            configure
        )
    }

    private fun multiPageSample(): Questionnaire {
        val questionItem1 = QuestionItem(
            0,
            QuestionType.SINGLE_ANSWER.id,
            "Single Answer",
            listOf(
                ResponseItem(0, "Response A"),
                ResponseItem(1, "Response B"),
                ResponseItem(2, "Response C"),
                ResponseItem(3, "Response D")
            )
        )
        val questionItem2 = QuestionItem(
            1,
            QuestionType.SINGLE_ANSWER.id,
            "Single ANswer",
            listOf(
                ResponseItem(0, "Response A"),
                ResponseItem(1, "Response B"),
                ResponseItem(2, "Response C"),
                ResponseItem(3, "Response D")
            )
        )
        val page1 = PageItem(
            0,
            listOf(questionItem1, questionItem2)
        )
        val page2 = PageItem(
            1,
            listOf()
        )
        return Questionnaire(
            5,
            "Sample of Multiple Pages",
            listOf(page1, page2)
        )
    }

    private fun mixedAnswerSample(): Questionnaire {
        val singleAnswer = QuestionItem(
            0,
            QuestionType.SINGLE_ANSWER.id,
            "Single Answer",
            listOf(
                ResponseItem(0, "Response A"),
                ResponseItem(1, "Response B"),
                ResponseItem(2, "Response C"),
                ResponseItem(3, "Response D")
            )
        )
        val multipleAnswers = QuestionItem(
            1,
            QuestionType.MULTIPLE_ANSWERS.id,
            "Multiple Answer",
            listOf(
                ResponseItem(1, "Response A"),
                ResponseItem(2, "Response B"),
                ResponseItem(3, "Response C"),
                ResponseItem(4, "Response D"),
                ResponseItem(CheckBoxItem.OTHER_RESPONSE_ID, "other")
            )
        )
        val openEnded = QuestionItem(
            3,
            QuestionType.OPEN_ENDED.id,
            "Open Ended",
            listOf(ResponseItem(0, ""))
        )
        val page = PageItem(
            0,
            listOf(singleAnswer, multipleAnswers, openEnded)
        )
        return Questionnaire(
            6,
            "Sample of Multiple Types Answer",
            listOf(page)
        )
    }
}