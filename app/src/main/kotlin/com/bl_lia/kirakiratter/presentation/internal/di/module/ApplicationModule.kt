package com.bl_lia.kirakiratter.presentation.internal.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.bl_lia.kirakiratter.BuildConfig
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.data.cache.AuthCache
import com.bl_lia.kirakiratter.data.cache.SharedPrefAuthCache
import com.bl_lia.kirakiratter.data.executor.JobExecutor
import com.bl_lia.kirakiratter.data.repository.*
import com.bl_lia.kirakiratter.data.type_adapter.AccountTypeAdapter
import com.bl_lia.kirakiratter.data.type_adapter.StatusTypeAdapter
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.repository.*
import com.bl_lia.kirakiratter.presentation.UiThread
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(
        private val application: Application
) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application = application

    @Provides
    @Singleton
    internal fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
            Retrofit.Builder().apply {
                baseUrl(BuildConfig.API_URL)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create(gson))
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }.build()

    @Provides
    @Singleton
    @Named("PushRetrofit")
    internal fun provideRetrofitForPush(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder().apply {
                client(okHttpClient)
                baseUrl("http://example.com")
                addConverterFactory(GsonConverterFactory.create())
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }.build()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(interceptor)
                addNetworkInterceptor(StethoInterceptor())
            }
        }.build()
    }

    @Provides
    @Singleton
    @Named("googleCloudClient")
    internal fun provideGoogleCloudClient(): Retrofit =
            Retrofit.Builder().apply {
                baseUrl(BuildConfig.GOOGLE_TRANSLATE_API_URL)
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                addConverterFactory(GsonConverterFactory.create())
                client(OkHttpClient.Builder().apply {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor.Level.BODY
                        } else {
                            HttpLoggingInterceptor.Level.NONE
                        }
                    })
                }.build())
            }.build()


    @Provides
    @Singleton
    internal fun provideAuthInterceptor(authCache: AuthCache): Interceptor {
        return Interceptor { chain ->
            val originalReq = chain.request()
            val builder = originalReq.newBuilder()
            val accessToken = authCache.accessToken()
            if (accessToken != null) {
                val value = "Bearer %s".format(accessToken.accessToken)
                builder.header("Authorization", value)
            }
            val request = builder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson =
            GsonBuilder().apply {
                setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                registerTypeAdapter(Status::class.java, StatusTypeAdapter())
                registerTypeAdapter(Account::class.java, AccountTypeAdapter())
            }.create()

    @Provides
    @Singleton
    fun provideThreadExecutor(executor: JobExecutor): ThreadExecutor = executor

    @Provides
    @Singleton
    fun providePostExecutionThread(thread: UiThread): PostExecutionThread = thread

    @Provides
    @Singleton
    internal fun provideAuthRepository(repository: AuthDataRepository): AuthRepository = repository

    @Provides
    @Singleton
    internal fun provideTimelineRepository(repository: TimelineDataRepository): TimelineRepository = repository

    @Provides
    @Singleton
    internal fun provideStatusRepository(repository: StatusDataRepository): StatusRepository = repository

    @Provides
    @Singleton
    internal fun provideTranslationRepository(repository: TranslationDataRepository): TranslationRepository = repository

    @Provides
    @Singleton
    internal fun provideNotificationRepository(repository: NotificationDataRepository): NotificationRepository = repository

    @Provides
    @Singleton
    internal fun provideAccountRepository(repository: AccountDataRepository): AccountRepository = repository

    @Provides
    @Singleton
    internal fun providePushNotificationRepository(repository: PushNotificationDataRepository): PushNotificationRepository = repository

    @Provides
    @Singleton
    internal fun provideAuthCache(cache: SharedPrefAuthCache): AuthCache = cache

    @Provides
    @Singleton
    @Named("appName")
    internal fun provideAppName(): String = application.getString(R.string.app_name)

    @Provides
    @Singleton
    @Named("oauthRedirectUri")
    internal fun provideOauthRedirectUri(): String {
        return "%s://%s/".format(application.getString(R.string.oauth_scheme), application.getString(R.string.oauth_redirect_host))
    }

    @Provides
    @Singleton
    @Named("oauthScopes")
    internal fun provideOauthScopes(): String = "read write follow"

    @Provides
    @Singleton
    @Named("appWebsite")
    internal fun provideAppWebsite(): String = application.getString(R.string.app_website)
}