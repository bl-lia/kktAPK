package com.bl_lia.kirakiratter.presentation.internal.di.module

import dagger.Module
import dagger.Provides
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.domain.interactor.auth.*
import javax.inject.Named

@Module
class AuthModule {

    @Provides
    @PerFragment
    @Named("getAuthInfo")
    internal fun provideGetAuthInfo(
            authRepository: AuthRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread): SingleUseCase<AuthInfo> {
        return GetAuthInfoUseCase(authRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("resetAuthInfo")
    internal fun provideResetAuthInfo(
            authRepository: AuthRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread): CompletableUseCase<AuthInfo> {
        return ResetAuthInfoUseCase(authRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("authenticateApp")
    internal fun provideAuthenticateApp(
            authRepository: AuthRepository,
            @Named("appName")
            appName: String,
            @Named("oauthRedirectUri")
            oauthRedirectUri: String,
            @Named("oauthScopes")
            oauthScopes: String,
            @Named("appWebsite")
            appWebsite: String,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
            ): SingleUseCase<AppCredentials> {

        return AuthenticateAppUseCase(
                authRepository = authRepository,
                appName = appName,
                oauthRedirectUri = oauthRedirectUri,
                oauthScopes = oauthScopes,
                appWebsite = appWebsite,
                threadExecutor = threadExecutor,
                postExecutionThread = postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("accessToken")
    internal fun provideFetchOAuthToken(
            authRepository: AuthRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<AccessToken> {
        return FetchOauthTokenUseCase(authRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("isAuthenticated")
    internal fun provideIsAuthenticated(
            authRepository: AuthRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Boolean> {
        return IsAuthenticatedUseCase(authRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("logout")
    internal fun provideLogout(
            authRepository: AuthRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): CompletableUseCase<Void> {
        return LogoutUseCase(authRepository, threadExecutor, postExecutionThread)
    }
}