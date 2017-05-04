package com.bl_lia.kirakiratter.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.adapter.timeline.TimelineFragmentPagerAdapter
import com.bl_lia.kirakiratter.presentation.adapter.timeline.TimelineSpinnerAdapter
import com.bl_lia.kirakiratter.presentation.fragment.NavigationDrawerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerTimelineActivityComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.TimelineActivityComponent
import com.bl_lia.kirakiratter.presentation.presenter.TimelineActivityPresenter
import kotlinx.android.synthetic.main.activity_timeline.*
import javax.inject.Inject

class TimelineActivity : AppCompatActivity() {

    val timelines: List<String> = listOf("home", "local")

    val spinnerAdapter: TimelineSpinnerAdapter by lazy {
        TimelineSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, timelines)
    }

    @Inject
    lateinit var presenter: TimelineActivityPresenter

    private var isSpinnerInitialized: Boolean = false

    private val component: TimelineActivityComponent by lazy {
        DaggerTimelineActivityComponent.builder()
                .applicationComponent((application as App).component)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        component.inject(this)

        view_pager.adapter = TimelineFragmentPagerAdapter(supportFragmentManager)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().also { transaction ->
                val fragment = NavigationDrawerFragment.newInstance()
                transaction.add(R.id.left_drawer, fragment)
            }.commit()
        }

        initView()
    }

    private fun initView() {
        button_katsu.setOnClickListener {
            val intent = Intent(this, KatsuActivity::class.java)
            startActivity(intent)
        }

        button_menu.setOnClickListener {
            if (!layout_drawer.isDrawerOpen(left_drawer)) {
                layout_drawer.openDrawer(left_drawer)
            }
        }

        spinner_timeline.adapter = spinnerAdapter
        spinner_timeline.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isSpinnerInitialized) {
                    isSpinnerInitialized = true
                    return
                }
                showTimeline(position)
                presenter.setSelectedTimeline(timelines[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                val position = spinner_timeline.selectedItemPosition
                view_pager.currentItem = position
            }
        }

        button_account.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
        button_notification.setOnClickListener { view_pager.setCurrentItem(2, false) }
        button_search.setOnClickListener { showNothing() }

        view_pager.post {
            presenter.getSelectedTimeline()
                    .subscribe { timeline, error ->
                        val index = timelines.indexOf(timeline)
                        if (index > -1) {
                            showTimeline(index)
                        }
                    }
        }
    }

    private fun showTimeline(position: Int) {
        when (position) {
            0 -> {
                spinner_timeline.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_home_black_24px, null)
                view_pager.setCurrentItem(0, false)
            }
            1 -> {
                spinner_timeline.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_group_black_24px, null)
                view_pager.setCurrentItem(1, false)
            }
        }
    }

    private fun showNothing() {
        Snackbar.make(layout_drawer, "まだないよ！", Snackbar.LENGTH_SHORT).show()
    }
}