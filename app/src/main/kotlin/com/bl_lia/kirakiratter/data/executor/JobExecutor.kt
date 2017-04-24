package com.bl_lia.kirakiratter.data.executor

import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import java.util.concurrent.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobExecutor
    @Inject constructor(): ThreadExecutor {

    companion object {
        private val INITIAL_POOL_SIZE: Int = 3
        private val MAX_POOL_SIZE: Int = 5
        private val KEEP_ALIVE_TIME: Long = 10
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }

    val workQueue: BlockingDeque<Runnable> by lazy {
        LinkedBlockingDeque<Runnable>()
    }

    val threadFactory: ThreadFactory by lazy {
        JobThreadFactory()
    }

    val threadPoolExecutor: ThreadPoolExecutor by lazy {
        ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue, threadFactory)
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }

        threadPoolExecutor.execute(runnable)
    }

    class JobThreadFactory : ThreadFactory {

        companion object {
            private val THREAD_NAME = "android_"
        }

        var counter: Int = 0

        override fun newThread(p0: Runnable?): Thread {
            return Thread(p0, THREAD_NAME + counter++)
        }
    }
}