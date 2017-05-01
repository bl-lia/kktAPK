package com.bl_lia.kirakiratter.presentation.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.extension.asHtml
import com.bl_lia.kirakiratter.presentation.fragment.AccountFragment
import com.bl_lia.kirakiratter.presentation.internal.di.component.AccountComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerAccountComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.ActivityModule
import com.bl_lia.kirakiratter.presentation.presenter.AccountPresenter
import com.bl_lia.kirakiratter.presentation.transform.AvatarTransformation
import com.squareup.picasso.Picasso
import io.reactivex.Single
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_account.*
import javax.inject.Inject

class AccountActivity : AppCompatActivity() {

    companion object {
        val INTENT_PARAM_ACCOUNT = "intent_param_account"
    }

    @Inject
    lateinit var presenter: AccountPresenter

    private var relationship: Relationship? = null
    private var isOtherAccount: Boolean = false

    private val account: Single<Account> by lazy {
        presenter.verifyCredentials()
                .map { me ->
                    if (intent.hasExtra(INTENT_PARAM_ACCOUNT)) {
                        val account = intent.getSerializableExtra(INTENT_PARAM_ACCOUNT) as Account
                        isOtherAccount = me.id != account.id
                        account
                    } else {
                        me
                    }
                }
    }

    private val component: AccountComponent by lazy{
        DaggerAccountComponent.builder()
                .applicationComponent((application as App).component)
                .activityModule(ActivityModule(this))
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        component.inject(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (savedInstanceState == null) {
            account.subscribe { account, error ->
                supportFragmentManager.beginTransaction().apply {
                    val fragment = AccountFragment.newInstance(account)
                    replace(R.id.layout_list, fragment)
                }.commit()
            }
        }

        initView()
    }

    private fun initView() {
        account.subscribe { account, error ->
            if (error != null) {
                showError(error)
                return@subscribe
            }

            layout_appbar.addOnOffsetChangedListener(AppBarOffsetChangedListener(layout_collapsing_toolbar, account.preparedDisplayName ?: " "))

            Picasso.with(this)
                    .load(account.avatar)
                    .transform(AvatarTransformation(ContextCompat.getColor(this, R.color.content_border)))
                    .into(image_avatar)
            if (account.header?.isNotEmpty() ?: false) {
                Picasso.with(this)
                        .load(account.header)
                        .transform(BlurTransformation(this))
                        .into(image_header)
            }

            text_accont_name.text = account.preparedDisplayName
            text_account_description.text = account.note?.asHtml()
            text_account_description.movementMethod = LinkMovementMethod.getInstance()

            presenter.relationship(account)
                    .subscribe { rel, error ->
                        if (error != null) {
                            showError(error)
                            return@subscribe
                        }

                        relationship = rel
                        text_followed_you.visibility = if (rel.followedBy) View.VISIBLE else View.GONE
                        invalidateOptionsMenu()
                    }

            image_header.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    loadHeaderImage(image_header.width, image_header.height)
                    image_header.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (relationship != null && isOtherAccount) {
            if (relationship?.following ?: false) {
                menu?.removeItem(R.id.menu_follow)
            } else {
                menu?.removeItem(R.id.menu_unfollow)
            }
        } else {
            menu?.clear()
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_account, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.menu_follow -> {
                    presenter.follow(account.blockingGet())
                            .subscribe { rel, error ->
                                if (error != null) {
                                    showError(error)
                                    return@subscribe
                                }

                                relationship = rel
                                invalidateOptionsMenu()
                            }
                    return true
                }
                R.id.menu_unfollow -> {
                    presenter.unfollow(account.blockingGet())
                            .subscribe { rel, error ->
                                if (error != null) {
                                    showError(error)
                                    return@subscribe
                                }

                                relationship = rel
                                invalidateOptionsMenu()
                            }
                    return true
                }
                android.R.id.home -> {
                    onBackPressed()
                    return true
                }
                else -> return false
            }
        } else {
            return false
        }
    }

    private fun loadHeaderImage(width: Int, height: Int) {
        account.subscribe { account ->
            if (account.header?.isNotEmpty() ?: false) {
                Picasso.with(this)
                        .load(account.header)
                        .resize(width, height)
                        .centerCrop()
                        .transform(BlurTransformation(this))
                        .into(image_header)
            }
        }
    }

    private fun showError(error: Throwable) {
        val messsage =
                if (error.localizedMessage.startsWith("HTTP 520")) {
                    resources.getString(R.string.error_message_5xx)
                } else {
                    error.localizedMessage
                }
        Snackbar.make(layout_content, messsage, Snackbar.LENGTH_LONG).show()
    }

    internal class AppBarOffsetChangedListener(val collapsingLayout: CollapsingToolbarLayout, val title: String) : AppBarLayout.OnOffsetChangedListener {

        private var isShow: Boolean = false
        private var scrollRange: Int = -1

        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            if (scrollRange == -1) {
                scrollRange = appBarLayout?.totalScrollRange ?: -1
            }

            if (scrollRange + (verticalOffset) == 0) {
                collapsingLayout.title = title
                isShow = true
            } else {
                collapsingLayout.title = " "
                isShow = false
            }
        }
    }
}