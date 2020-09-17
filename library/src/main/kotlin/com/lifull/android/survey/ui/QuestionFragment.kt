package com.lifull.android.survey.ui

import android.content.Context
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.util.forEach
import androidx.core.util.isEmpty
import androidx.core.util.valueIterator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lifull.android.survey.data.MessageRow
import com.lifull.android.survey.QuestionType
import com.lifull.android.survey.R
import com.lifull.android.survey.data.Row
import com.lifull.android.survey.data.*
import com.lifull.android.survey.data.local.QuestionnaireConfigure
import com.lifull.android.survey.ui.multipleAnswers.MultipleAnswersAdapter
import com.lifull.android.survey.ui.multipleAnswers.MultipleAnswersViewModel
import com.lifull.android.survey.ui.openEnded.OpenEndedAdapter
import com.lifull.android.survey.ui.openEnded.OpenEndedViewModel
import com.lifull.android.survey.ui.singleAnswer.SingleAnswerAdapter
import com.lifull.android.survey.ui.singleAnswer.SingleAnswerViewModel
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question.view.*
import java.lang.IllegalStateException

/**
 * Question Fragment
 */
class QuestionFragment : Fragment()
{
    companion object {

        /** Const: Page */
        const val ARGS_PAGE = "page"
        /** Const: Configure */
        const val ARGS_CONFIGURE = "configure"
        /** Const: Presence of absence ot the next page */
        const val ARGS_HAS_NEXT = "has_next"

        /**
         * Get an instance of [QuestionFragment]
         *
         * @param pageData question page
         * @param configure configure
         * @param hasNext Presence of absence of the next page
         * @return [QuestionFragment] Instance
         */
        fun newInstance(
            pageData: PageData,
            configure: QuestionnaireConfigure,
            hasNext: Boolean
        ) = QuestionFragment().apply {
            arguments = bundleOf(
                ARGS_PAGE to pageData,
                ARGS_CONFIGURE to configure,
                ARGS_HAS_NEXT to hasNext
            )
        }
    }

    /** Page */
    private val pageData: PageData by lazy {
        requireArguments().getParcelable<PageData>(ARGS_PAGE)!!
    }
    /** Configure */
    private val configure: QuestionnaireConfigure by lazy {
        requireArguments().getParcelable<QuestionnaireConfigure>(ARGS_CONFIGURE)!!
    }
    /** Presence of absence of the next page */
    private val hasNext: Boolean by lazy {
        requireArguments().getBoolean(ARGS_HAS_NEXT)
    }
    /** ViewModel */
    private val viewModel: QuestionFragmentViewModel by lazy {
        ViewModelProvider(this, QuestionFragmentViewModel.Factory())
            .get(QuestionFragmentViewModel::class.java)
    }

    /** Adapters */
    private val adapters = arrayListOf<RecyclerView.Adapter<*>>()
    /** MergeAdapter */
    private var adapter: MergeAdapter? = null
    /** Callback */
    private var callback: OnClickListener? = null


    /**
     * @inheritDoc
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = parentFragment as OnClickListener

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                callback?.onClickBack(pageData.id)
            }
        })
    }

    /**
     * @inheritDoc
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    /**
     * @inheritDoc
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button_back.text = configure.back ?: getString(R.string.back)

        view.button_next.text = if (!configure.back.isNullOrEmpty()) {
            if (hasNext) {
                getString(R.string.next)
            } else {
                getString(R.string.submit)
            }
        } else {
            ""
        }

        view.button_back.setOnClickListener {
            callback?.onClickBack(pageData.id)
        }
        view.button_next.setOnClickListener {
            if (!isAnswered()) {
                return@setOnClickListener
            }

            if (hasNext) {
                callback?.onClickNext(pageData.id, viewModel.responses)
            } else {
                callback?.onClickSubmit(pageData.id, viewModel.responses)
            }
        }
    }

    /**
     * @inheritDoc
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.onBackPressedDispatcher

        createAdapter()
        recyclerView.adapter = adapter

        subscribeUi(viewModel)
    }

    /**
     * @inheritDoc
     */
    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView.adapter = null
        view?.let {
            it.button_back.setOnClickListener(null)
            it.button_next?.setOnClickListener(null)
        }
    }

    /**
     * @inheritDoc
     */
    override fun onDestroy() {
        super.onDestroy()

        adapter = null
    }

    /**
     * Create Adapter
     */
    private fun createAdapter() {
        if (adapter != null) {
            return
        }
        pageData.questions.forEachIndexed { index, sectionData ->
            val items = arrayListOf<Row>()
            items.add(
                MessageRow(
                    0,
                    sectionData.message
                )
            )
            items.addAll(sectionData.choice.rows)

            when (sectionData.choice.type) {
                QuestionType.SINGLE_ANSWER -> {
                    val vm = ViewModelProvider(this, SingleAnswerViewModel.Factory(sectionData.id))
                        .get("${viewModel.uuid}+${index}", SingleAnswerViewModel::class.java)
                    vm.items(items)
                    viewModel.viewModels.put(index, vm)
                    adapters.add(SingleAnswerAdapter(vm.callback))
                }
                QuestionType.MULTIPLE_ANSWERS -> {
                    val vm = ViewModelProvider(this, MultipleAnswersViewModel.Factory(sectionData.id))
                        .get("${viewModel.uuid}+${index}", MultipleAnswersViewModel::class.java)
                    vm.items(items)
                    viewModel.viewModels.put(index, vm)
                    adapters.add(MultipleAnswersAdapter(vm.callback))
                }
                QuestionType.OPEN_ENDED -> {
                    val vm = ViewModelProvider(this, OpenEndedViewModel.Factory(sectionData.id))
                        .get("${viewModel.uuid}+${index}", OpenEndedViewModel::class.java)
                    vm.items(items)
                    viewModel.viewModels.put(index, vm)
                    adapters.add(OpenEndedAdapter(vm.callback))
                }
                else -> {
                    throw IllegalStateException()
                }
            }
        }
        adapter = MergeAdapter(adapters)
    }

    /**
     * UI
     *
     * @param viewModel [QuestionFragmentViewModel]
     */
    private fun subscribeUi(viewModel: QuestionFragmentViewModel) {
        viewModel.viewModels.forEach { key, value ->
            value.getItemsLiveData().observe(viewLifecycleOwner, Observer { items ->
                val adapter = adapters.getOrNull(key) as? ListAdapter<Item, *>
                adapter?.submitList(items)
            })
            value.getResponseLiveData().observe(viewLifecycleOwner, Observer { response ->
                viewModel.responses.put(key, response)

                button_next.isEnabled = isAnswered()
            })
        }
    }

    /**
     * Get a Answer Status
     *
     * @return true: Answered / false: UnAnswered
     */
    private fun isAnswered(): Boolean {
        if (viewModel.viewModels.isEmpty()) {
            return false
        }
        viewModel.viewModels.valueIterator().forEach { viewModel ->
            if (viewModel.getResponseLiveData().value.isComplete()) {
                return false
            }
        }
        return true
    }
}

/**
 * Callback Interface
 */
interface OnClickListener {

    /**
     * Called when the next is clicked
     *
     * @param id Page ID
     * @param responses Response
     */
    fun onClickNext(id: Int, responses: SparseArray<Response>)

    /**
     * Called when the back is clicked
     *
     * @param id Page ID
     */
    fun onClickBack(id: Int)

    /**
     * Called when the submit is clicked
     *
     * @param id Page ID
     * @param responses Response
     */
    fun onClickSubmit(id: Int, responses: SparseArray<Response>)
}