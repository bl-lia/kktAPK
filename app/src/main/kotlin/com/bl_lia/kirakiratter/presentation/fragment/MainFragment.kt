package com.bl_lia.kirakiratter.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.activity.KatsuActivity
import com.bl_lia.kirakiratter.presentation.activity.TimelineActivity
import com.bl_lia.kirakiratter.presentation.internal.di.component.AuthComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerAuthComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.presenter.MainPresenter
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    @Inject
    lateinit var presenter: MainPresenter

    private val component: AuthComponent by lazy {
        DaggerAuthComponent.builder()
                .applicationComponent((activity.application as App).component)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    private val sharedText: String? by lazy {
        if (activity.intent.action == Intent.ACTION_SEND
                && activity.intent.type == "text/plain") {
            activity.intent.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
    }

    private val sharedImage: Uri? by lazy {
        if (activity.intent.action == Intent.ACTION_SEND
                && activity.intent.type.startsWith("image/")) {
            activity.intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        } else {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_login.setOnClickListener {
            button_login.isEnabled = false
            presenter.authenticate()
                    .subscribe { credentials, error ->
                        button_login.isEnabled = true
                        if (error != null) {
                            showError(error)
                            return@subscribe
                        }

                        presenter.redirect(credentials.clientId)
                    }
        }
    }

    override fun onResume() {
        super.onResume()

        presenter.isAuthenticated()
                .subscribe { authenticated ->
                    if (authenticated) {
                        if (activity != null) {
                            if (sharedText != null || sharedImage != null) {
                                val intent = Intent(activity, KatsuActivity::class.java).apply {
                                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    putExtra(KatsuActivity.INTENT_PARAM_SHARED_TEXT, sharedText)
                                    putExtra(KatsuActivity.INTENT_PARAM_SHARED_IMAGE, sharedImage)
                                }
                                startActivity(intent)
                            } else {
                                val intent = Intent(activity, TimelineActivity::class.java).apply {
                                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                }
                                startActivity(intent)
                            }
                        }
                    }
                }
    }

    override fun onStart() {
        super.onStart()

        if (presenter.fromLogin(activity.intent.data)) {
            val code = activity.intent.data.getQueryParameter("code")
            code?.let {
                presenter.fetchToken(code)
                        .subscribe { accessToken, error ->
                            if (error != null) {
                                showError(error)
                                return@subscribe
                            }

                            if (accessToken != null) {
                                val intent = Intent(activity, TimelineActivity::class.java).apply {
                                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                }
                                startActivity(intent)
                                activity.finish()
                            }
                        }
            }
        }
    }

    private fun showError(error: Throwable) {
        Snackbar.make(layout_content, error.localizedMessage, Snackbar.LENGTH_LONG).show()
    }
}