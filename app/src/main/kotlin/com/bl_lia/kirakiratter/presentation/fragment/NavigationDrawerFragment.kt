package com.bl_lia.kirakiratter.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.BuildConfig
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.activity.LicenseActivity
import com.bl_lia.kirakiratter.presentation.activity.MainActivity
import com.bl_lia.kirakiratter.presentation.activity.ThanksActivity
import com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer.NavigationDrawerAdapter
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerNavigationDrawerComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.NavigationDrawerComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.presenter.NavigationDrawerPresenter
import com.crashlytics.android.Crashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.fragment_navigation_drawer.*
import javax.inject.Inject

class NavigationDrawerFragment : Fragment() {

    companion object {
        fun newInstance(): NavigationDrawerFragment = NavigationDrawerFragment()
    }

    @Inject
    lateinit var presenter: NavigationDrawerPresenter

    lateinit var pushUrl: String

    val component: NavigationDrawerComponent by lazy {
        DaggerNavigationDrawerComponent.builder()
                .applicationComponent((activity.application as App).component)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    val adapter: NavigationDrawerAdapter by lazy {
        NavigationDrawerAdapter().apply {
            onClickMenu.subscribe { menu ->
                when (menu) {
                    NavigationDrawerAdapter.Menu.License -> {
                        val intent = Intent(activity, LicenseActivity::class.java)
                        startActivity(intent)
                    }
                    NavigationDrawerAdapter.Menu.Thanks -> {
                        val intent = Intent(activity, ThanksActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_navigation_drawer, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app_version.text = "version: %s : %s".format(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        drawer_list_menu.adapter = adapter
        drawer_list_menu.layoutManager = LinearLayoutManager(activity)

        button_logout.setOnClickListener {
            presenter.logout()
                    .subscribe {
                        val intent = Intent(activity, MainActivity::class.java).apply {
                            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                        startActivity(intent)
                    }
        }

        val fbc = FirebaseRemoteConfig.getInstance()
        fbc.fetch()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fbc.activateFetched()
                        pushUrl = fbc.getString("push_api_url")
                        val pushFeatureEnabled = fbc.getBoolean("push_feature_enabled")
                        adapter.pushFeatureEnabled = !pushUrl.isNullOrEmpty() && !pushUrl.startsWith("nil") && pushFeatureEnabled
                        adapter.notifyDataSetChanged()
                        presenter.isRegisteredToken(pushUrl)
                                .subscribe { registered, error ->
                                    if (error != null) {
                                        Crashlytics.logException(error)
                                        return@subscribe
                                    }
                                    adapter.pushEnabled(registered)
                                }
                    }
                }

        adapter.onChangePushNotificationSetting
                .subscribe { checked ->
                    if (checked) {
                        presenter.registerToken(pushUrl)
                                .subscribe { response, error ->
                                    if (error != null) {
                                        Crashlytics.logException(error)
                                        adapter.pushEnabled(false)
                                        return@subscribe
                                    }
                                }
                    } else {
                        presenter.unregisterToken(pushUrl)
                                .subscribe { response, error ->
                                    if (error != null) {
                                        Crashlytics.logException(error)
                                        adapter.pushEnabled(true)
                                        return@subscribe
                                    }
                                }
                    }
                }
    }
}