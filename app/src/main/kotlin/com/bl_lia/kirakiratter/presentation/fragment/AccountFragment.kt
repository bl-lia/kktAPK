package com.bl_lia.kirakiratter.presentation.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.preparedErrorMessage
import com.bl_lia.kirakiratter.domain.value_object.Translation
import com.bl_lia.kirakiratter.presentation.activity.FullImageViewActivity
import com.bl_lia.kirakiratter.presentation.activity.KatsuActivity
import com.bl_lia.kirakiratter.presentation.adapter.account.AccountAdapter
import com.bl_lia.kirakiratter.presentation.internal.di.component.AccountFragmentComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerAccountFragmentComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.presenter.AccountFragmentPresenter
import com.bl_lia.kirakiratter.presentation.scroll_listener.TimelineScrollListener
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

class AccountFragment : RxFragment() {

    companion object {
        fun newInstance(account: Account): AccountFragment =
                AccountFragment().also { fragment ->
                    fragment.arguments = Bundle().also { args ->
                        args.putSerializable("account", account)
                    }
                }
    }

    @Inject
    lateinit var presenter: AccountFragmentPresenter

    private var moreLoading: Boolean = false

    private val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val scrollListener: TimelineScrollListener by lazy {
        object : TimelineScrollListener(layoutManager as LinearLayoutManager) {
            override fun onLoadMore() {
                adapter.maxId?.let { maxId ->
                    if (moreLoading) return@let
                    moreLoading = true

                    presenter.fetchMoreStatus(account, maxId)
                            ?.compose(bindToLifecycle())
                            ?.doAfterTerminate { moreLoading = false }
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

    private val account: Account by lazy {
        arguments.getSerializable("account") as Account
    }

    private val adapter: AccountAdapter by lazy {
        AccountAdapter()
    }

    private val component: AccountFragmentComponent by lazy {
        DaggerAccountFragmentComponent.builder()
                .applicationComponent((activity.application as App).component)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (list_status.layoutManager == null) {
            list_status.layoutManager = layoutManager
        }

        list_status.adapter = adapter
        list_status.addOnScrollListener(scrollListener)

        adapter.onClickReply.subscribe { status ->
            val target = status.reblog ?: status
            val intent = Intent(activity, KatsuActivity::class.java).apply {
                putExtra(KatsuActivity.INTENT_PARAM_REPLY_ACCOUNT_NAME, target.account?.userName)
                putExtra(KatsuActivity.INTENT_PARAM_REPLY_STATUS_ID, target.id)
            }
            startActivity(intent)
        }
        adapter.onClickReblog.subscribe { status ->
            val target = status.reblog ?: status
            presenter.reblog(target)
                    .compose(bindToLifecycle())
                    .subscribe { status, error ->
                        if (error != null) {
                            showError(error)
                            return@subscribe
                        }

                        val updateTarget = status.reblog ?: status
                        adapter.update(updateTarget)
                    }
        }
        adapter.onClickFavourite.subscribe { status ->
            val target = status.reblog ?: status
            presenter.favourite(target)
                    .compose(bindToLifecycle())
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
        adapter.onClickMoreMenu.subscribe { (status, view) ->
            val menu = PopupMenu(context, view)
            menu.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.menu_share_text -> {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, status.toSummarizedText(context))
                        }
                        startActivity(intent)
                        true
                    }
                    R.id.menu_copy_link -> {
                        val url = status.reblog?.url ?: status.url
                        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboardManager.primaryClip = ClipData.newPlainText("URL", url)
                        true
                    }
                    else -> false
                }
            }
            menu.inflate(R.menu.menu_katsu)
            menu.show()
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

        layout_swipe_refresh?.setOnRefreshListener {
            fetch()
        }

        fetch()
    }

    fun tranlateText(status: Status, translations: List<Translation>, error: Throwable?) {
        if (error != null) {
            showError(error)
            return
        }
        adapter.addTranslatedText(status, translations.first().translatedText)
    }

    private fun fetch() {
        layout_swipe_refresh?.isRefreshing = true
        presenter.fetchStatus(account)
                .doAfterTerminate {
                    layout_swipe_refresh?.isRefreshing = false
                }
                .compose(bindToLifecycle())
                .subscribe { list, error ->
                    if (error != null) {
                        showError(error)
                        return@subscribe
                    }

                    adapter.reset(list)
                }
    }

    private fun showError(error: Throwable) {
        Snackbar.make(layout_content, error.preparedErrorMessage(activity), Snackbar.LENGTH_LONG).show()
    }
}