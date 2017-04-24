package com.bl_lia.kirakiratter.presentation.presenter

import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import io.reactivex.Single
import okhttp3.HttpUrl
import javax.inject.Inject
import javax.inject.Named

@PerFragment
class MainPresenter
    @Inject constructor(
            private val fragment: Fragment,
            @Named("getAuthInfo")
            private val getAuthInfo: SingleUseCase<AuthInfo>,
            @Named("resetAuthInfo")
            private val resetAuthInfo: CompletableUseCase<AuthInfo>,
            @Named("authenticateApp")
            private val authenticateApp: SingleUseCase<AppCredentials>,
            @Named("accessToken")
            private val fetchOAuthToken: SingleUseCase<AccessToken>,
            @Named("isAuthenticated")
            private val isAuthenticated: SingleUseCase<Boolean>,
            @Named("oauthRedirectUri")
            private val oauthRedirectUri: String,
            @Named("oauthScopes")
            private val oauthScopes: String
            ) : Presenter {

    override fun start() {
    }

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun authenticate(): Single<AppCredentials> {
        return authenticateApp.execute()
    }

    fun isAuthenticated(): Single<Boolean> {
        return isAuthenticated.execute()
    }

    fun redirect(clientId: String) {
        val endpoint = "/oauth/authorize"
        val url = HttpUrl.Builder().apply {
            scheme("https")
            host("kirakiratter.com")
            encodedPath(endpoint)
            addQueryParameter("client_id", clientId)
            addQueryParameter("redirect_uri", oauthRedirectUri)
            addQueryParameter("response_type", "code")
            addQueryParameter("scope", oauthScopes)
        }.build()

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
        fragment.startActivity(intent)
    }

    fun fromLogin(uri: Uri?): Boolean {
        return uri?.toString()?.startsWith(oauthRedirectUri) ?: false
    }

    fun fetchToken(code: String): Single<AccessToken> {
        return fetchOAuthToken.execute(code)
    }
}