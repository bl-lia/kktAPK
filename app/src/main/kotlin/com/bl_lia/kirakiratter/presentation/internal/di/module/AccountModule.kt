package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.account.*
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AccountModule {

    @Provides
    @PerActivity
    @Named("listAccountStatus")
    fun listAccountStatus(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return ListAccountStatusUseCase(accountRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerActivity
    @Named("listMoreAccountStatus")
    fun listMoreAccountStatus(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return ListMoreAccountStatusUseCase(accountRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerActivity
    @Named("getRelationship")
    fun relationship(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Relationship> {
        return GetRelationshipUseCase(accountRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerActivity
    @Named("follow")
    fun follow(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Relationship> {
        return FollowAccountUseCase(accountRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerActivity
    @Named("unfollow")
    fun unfollow(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Relationship> {
        return UnFollowAccountUseCase(accountRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerActivity
    @Named("verifyCredentials")
    fun verifyCredentials(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Account> {
        return VerifyCredentialsUseCase(accountRepository, threadExecutor, postExecutionThread)
    }
}