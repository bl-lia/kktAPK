package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.account.ListAccountStatusUseCase
import com.bl_lia.kirakiratter.domain.interactor.account.ListMoreAccountStatusUseCase
import com.bl_lia.kirakiratter.domain.repository.AccountRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AccountFragmentModule {
    @Provides
    @PerFragment
    @Named("listAccountStatus")
    fun listAccountStatus(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return ListAccountStatusUseCase(accountRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("listMoreAccountStatus")
    fun listMoreAccountStatus(
            accountRepository: AccountRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return ListMoreAccountStatusUseCase(accountRepository, threadExecutor, postExecutionThread)
    }
}