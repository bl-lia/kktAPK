package com.bl_lia.kirakiratter.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.preparedErrorMessage
import com.bl_lia.kirakiratter.domain.value_object.Translation
import com.bl_lia.kirakiratter.presentation.activity.AccountActivity
import com.bl_lia.kirakiratter.presentation.activity.FullImageViewActivity
import com.bl_lia.kirakiratter.presentation.activity.KatsuActivity
import com.bl_lia.kirakiratter.presentation.adapter.timeline.TimelineAdapter
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerTimelineComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.TimelineComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.presenter.TimelinePresenter
import com.bl_lia.kirakiratter.presentation.scroll_listener.TimelineScrollListener
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TimelineFragment : Fragment(), ScrollableFragment {

    enum class Scope {
        Home, Local
    }

    companion object {
        fun newInstance(scope: Scope = Scope.Home): TimelineFragment =
                TimelineFragment().also { fragment ->
                    val args = Bundle().apply {
                        putSerializable(PARAM_SCOPE, scope)
                    }
                    fragment.arguments = args
                }

        const val PARAM_SCOPE = "param_scope"
    }

    private var scope: Scope = Scope.Home

    @Inject
    lateinit var presenter: TimelinePresenter

    private var moreLoading: Boolean = false
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val component: TimelineComponent by lazy {
        DaggerTimelineComponent.builder()
                .applicationComponent((activity.application as App).component)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    private val adapter: TimelineAdapter by lazy {
        TimelineAdapter()
    }

    private val scrollListener: TimelineScrollListener by lazy {
        object : TimelineScrollListener(layoutManager as LinearLayoutManager) {
            override fun onLoadMore() {
                adapter.maxId?.let { maxId ->
                    if (moreLoading) return@let
                    moreLoading = true
                    presenter.fetchMoreTimeline(scope, maxId)
                            ?.doAfterTerminate {
                                moreLoading = false
                            }
                            ?.subscribe { list, error ->
                                if (error != null) {
                                    showError(error)
                                    return@subscribe
                                }

                                adapter.add(list)
                            }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

        scope = arguments.getSerializable(PARAM_SCOPE) as Scope
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_timeline, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (timeline.layoutManager == null) {
            layoutManager = LinearLayoutManager(activity)
            timeline.layoutManager = layoutManager
        }

        timeline.adapter = adapter
        timeline.addOnScrollListener(scrollListener)
        adapter.onClickReply.subscribe { status ->
            val target = status.reblog ?: status
            val intent = Intent(activity, KatsuActivity::class.java).apply {
                putExtra(KatsuActivity.INTENT_PARAM_REPLY_ACCOUNT_NAME, target.account?.userName)
                putExtra(KatsuActivity.INTENT_PARAM_REPLY_STATUS_ID, target.id)
            }
            startActivity(intent)
        }
        adapter.onClickReblog.subscribe { status ->
            presenter.reblog(status)
                    .subscribe { status, error ->
                        if (error != null) {
                            showError(error)
                            return@subscribe
                        }

                        status.reblog?.let {
                            adapter.update(it)
                        }
                    }
        }
        adapter.onClickFavourite.subscribe { status ->
            presenter.favourite(status)
                    .subscribe { status, error ->
                        if (error != null) {
                            showError(error)
                            return@subscribe
                        }

                        adapter.update(status)
                    }
        }
        adapter.onClickTranslate.subscribe { status ->
            val target = status.reblog ?: status
            if (target.content?.translatedText.isNullOrEmpty()) {
                presenter.translation(status)
            }
        }
        adapter.onClickMedia.subscribe { triple ->
            val status = triple.first
            val index = triple.second
            val imageView = triple.third
            val target = status.reblog ?: status
            val mediaList = target.mediaAttachments
                    .filter { !it.url.isNullOrEmpty() }
                    .map { it.url!! }
            val previewList = target.mediaAttachments
                    .filter { !it.previewUrl.isNullOrEmpty() }
                    .map { it.previewUrl!! }

            val intent = FullImageViewActivity.newIntent(activity, ArrayList(mediaList), ArrayList(previewList), index)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "image")
            startActivity(intent, options.toBundle())
        }
        adapter.onClickAccount.subscribe { pair ->
            val account = pair.first
            val imageView = pair.second
            val intent = Intent(activity, AccountActivity::class.java).apply {
                putExtra(AccountActivity.INTENT_PARAM_ACCOUNT, account)
            }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "avatar")
            startActivity(intent, options.toBundle())
        }

        fetch(scope)

        layout_swipe_refresh?.setOnRefreshListener {
            fetch(scope, newTimeline = true)
        }
    }

    override fun scrollToTop() {
        timeline?.smoothScrollToPosition(0)
    }

    private fun fetch(scope: Scope, newTimeline: Boolean = false) {
        layout_swipe_refresh?.isRefreshing = true
        presenter.fetchTimeline(scope, newTimeline)
                .doAfterTerminate {
                    layout_swipe_refresh?.isRefreshing = false
                }
                .subscribe { list, error ->
                    if (error != null) {
                        showError(error)
                        return@subscribe
                    }

                    val lastSinceId = adapter.sinceId
                    adapter.reset(list)

                    lastSinceId?.toInt()?.let { lastId ->
                        list.indexOfFirst { it.id <= lastId }.let { nextPosition ->
                            if(nextPosition >= 0) {
                                timeline.scrollToPosition(nextPosition)
                            }
                        }
                    }
                }
    }

    fun tranlateText(status: Status, translations: List<Translation>, error: Throwable?) {
        if (error != null) {
            showError(error)
            return
        }
        adapter.addTranslatedText(status, translations.first().translatedText)
    }


    private fun showError(error: Throwable) {
        Snackbar.make(layout_content, error.preparedErrorMessage(activity), Snackbar.LENGTH_LONG).show()
    }
}