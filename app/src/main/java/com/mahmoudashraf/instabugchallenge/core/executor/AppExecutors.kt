package com.mahmoudashraf.instabugchallenge.core.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */

class AppExecutorsManager {
    val diskIO: Executor
    val networkIO: Executor
    val mainThread: Executor

    init {
        this.diskIO = Executors.newSingleThreadExecutor()
        this.networkIO = Executors.newFixedThreadPool(3)
        this.mainThread = MainThreadExecutor()
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}

object AppExecutors {
    val instance: AppExecutorsManager = AppExecutorsManager()
}
