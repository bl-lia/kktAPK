package com.bl_lia.kirakiratter.presentation.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account

class AccountFragment : Fragment() {

    companion object {
        fun newInstance(account: Account): AccountFragment =
                AccountFragment().also { fragment ->
                    fragment.arguments = Bundle().also { args ->
                        args.putSerializable("account", account)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val account = arguments["account"] as Account
//        Picasso.with(activity)
//                .load(account.avatar)
//                .transform(AvatarTransformation(ContextCompat.getColor(activity, R.color.content_border)))
//                .into(image_avatar)
//        if (account.header?.isNotEmpty() ?: false) {
//            Picasso.with(activity)
//                    .load(account.header)
//                    .transform(BlurTransformation(activity))
//                    .into(image_header)
//        }
//
//        text_accont_name.text =
//                if (account.displayName.isNullOrEmpty()) {
//                    account.userName
//                } else {
//                    account.displayName
//                }
//
//        text_account_description.text = account.note
    }
}