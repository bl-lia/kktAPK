package com.bl_lia.kirakiratter.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.activity.TimelineActivity
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerStatusComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.StatusComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.presenter.KatsuPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_katsu.*
import kotlinx.android.synthetic.main.list_item_katsu_image.view.*
import javax.inject.Inject

class KatsuFragment : Fragment() {

    companion object {
        fun newInstance(accountName: String? = null, replyStatusId: Int? = null, sharedText: String? = null, sharedImages: ArrayList<Uri> = arrayListOf()): KatsuFragment =
                KatsuFragment().also { fragment ->
                    val bundle = Bundle().apply {
                        if (!accountName.isNullOrEmpty()) putString(PARAM_ACCOUNT_NAME, accountName)
                        if (replyStatusId != null && replyStatusId > -1) putInt(PARAM_REPLY_STATUS_ID, replyStatusId)
                        putString(PARAM_SHARED_TEXT, sharedText)
                        putParcelableArrayList(PARAM_SHARED_IMAGE, sharedImages)
                    }
                    fragment.arguments = bundle
                }

        private val REQUEST_PICK_IMAGE = 1
        private val PARAM_ACCOUNT_NAME = "param_account_name"
        private val PARAM_REPLY_STATUS_ID = "param_reply_status_id"
        private val PARAM_SHARED_TEXT = "param_shared_text"
        private val PARAM_SHARED_IMAGE = "param_shared_image"
        private val MAX_IMAGE_COUNT = 4
    }

    @Inject
    lateinit var presenter: KatsuPresenter

    val mediaUris = mutableListOf<Uri>()

    private val component: StatusComponent by lazy {
        DaggerStatusComponent.builder()
                .applicationComponent((activity.application as App).component)
                .fragmentModule(FragmentModule(this))
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_katsu, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_katsu.setOnClickListener {
            val header = content_warning_edittext.text.toString()
            val body = katsu_content_body.text.toString()
            val replyTo: Int? =
                    if (arguments.containsKey(PARAM_REPLY_STATUS_ID)) {
                        arguments.getInt(PARAM_REPLY_STATUS_ID)
                    } else {
                        null
                    }

            if (body.isNotEmpty()) {
                button_katsu.isEnabled = false
                presenter.post(
                        text = body,
                        warning = header,
                        attachment = mediaUris,
                        inReplyToId = replyTo
                ).subscribe { status, error ->
                    button_katsu.isEnabled = true
                    if (error != null) {
                        showError(error)
                        return@subscribe
                    }

                    val intent = Intent(activity, TimelineActivity::class.java).apply {
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }

        switch_content_warning.setOnCheckedChangeListener { button, checked ->
            content_warning_textinput.visibility = if (checked) View.VISIBLE else View.GONE
        }

        attach_image_1.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                setType("image/*")
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(intent, REQUEST_PICK_IMAGE)
        }

        katsu_content_body.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setKatsuButtonVisibility()
            }
        })

        if (arguments.containsKey(PARAM_ACCOUNT_NAME)) {
            val accountText = "@%s ".format(arguments.getString(PARAM_ACCOUNT_NAME))
            katsu_content_body.setText(accountText, TextView.BufferType.NORMAL)
            katsu_content_body.setSelection(accountText.length)
        }

        setHint()

        if (arguments.containsKey(PARAM_SHARED_TEXT)) {
            arguments.getString(PARAM_SHARED_TEXT)?.let { sharedText ->
                katsu_content_body.setText("%s ".format(sharedText))
                katsu_content_body.setSelection(sharedText.length + 1)
            }
        }

        if (arguments.containsKey(PARAM_SHARED_IMAGE)) {
            arguments.getParcelableArrayList<Uri>(PARAM_SHARED_IMAGE)?.forEach { sharedImage ->
                attachImage(sharedImage)
            }
        }

        setKatsuButtonVisibility()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
                && requestCode == REQUEST_PICK_IMAGE) {
            data?.let {
                data.clipData?.let {
                    (0..data.clipData.itemCount - 1).map { data.clipData.getItemAt(it) }.forEach { item ->
                        attachImage(item.uri)
                    }
                }
                data.data?.let {
                    attachImage(data.data)
                }
            }
            setKatsuButtonVisibility()
        }
    }

    private fun setHint() {
        val hintHeader = content_warning_textinput.hint
        content_warning_textinput.hint = null
        content_warning_textinput.editText?.hint = hintHeader

        val hintContent = layout_content_body.hint
        layout_content_body.hint = null
        layout_content_body.editText?.hint = hintContent
    }

    private fun showError(error: Throwable) {
        Snackbar.make(layout_content, error.localizedMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun setKatsuButtonVisibility() {
        val body = katsu_content_body.text.toString()

        button_katsu.isEnabled = body.isNotEmpty() || mediaUris.size > 0

        attach_image_1.isEnabled = mediaUris.size < MAX_IMAGE_COUNT
    }

    private fun attachImage(imageUri: Uri) {
        if(mediaUris.size >= MAX_IMAGE_COUNT) {
            Snackbar.make(layout_content, R.string.post_image_over_max, Snackbar.LENGTH_LONG).show()
            return
        }

        mediaUris.add(imageUri)

        val itemView = activity.layoutInflater.inflate(R.layout.list_item_katsu_image, null)
        attach_image_list.addView(itemView, attach_image_list.childCount)
        itemView.attach_image_remove.setOnClickListener {
            removeImage(imageUri)
            setKatsuButtonVisibility()
        }

        val imageView = itemView.attach_image_content
        Picasso.with(activity)
                .load(imageUri)
                .fit()
                .centerInside()
                .into(imageView)
    }

    private fun removeImage(imageUri: Uri) {
        val index = mediaUris.indexOf(imageUri)
        if(index < 0) return

        mediaUris.removeAt(index)
        attach_image_list.removeViewAt(index)
    }
}