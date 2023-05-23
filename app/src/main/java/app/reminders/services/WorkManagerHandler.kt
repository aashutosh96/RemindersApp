package app.reminders.services

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import app.reminders.data.database.AppDao
import com.google.common.util.concurrent.ListenableFuture
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MyCallback {
    fun onSuccess()
    fun onError()
}

class WorkManagerHandler(val context: Context, private val workerParams: WorkerParameters): ListenableWorker(context, workerParams), KoinComponent {
    private val appDao: AppDao by inject()

    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture {
            val callback = object : MyCallback {
                override fun onSuccess() {
                    it.set(Result.success())
                }

                override fun onError() {
                    it.set(Result.failure())
                }
            }
            initReminders(callback)
            return@getFuture callback
        }
    }

    private fun initReminders(callback: MyCallback) {
        TODO("Not yet implemented")
    }
}