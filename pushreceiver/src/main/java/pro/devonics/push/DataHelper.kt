package pro.devonics.push

import android.util.Log
import pro.devonics.push.model.PushData
import pro.devonics.push.network.ApiHelper
import pro.devonics.push.network.RetrofitBuilder
import java.util.*

private const val TAG = "DataHelper"

class DataHelper {

    companion object {

        private val service = ApiHelper(RetrofitBuilder.apiService)

        private var startTime: Long = 0
        private var stopTime: Long = 0

        fun getDuration(): Long {
            val date = Calendar.getInstance().timeInMillis
            stopTime = date

            return (stopTime - startTime) / 1000
        }

        fun startTime() {
            val date = Calendar.getInstance().timeInMillis
            startTime = date
            //Log.d(TAG, "onCreate: startTime = $startTime")
        }

        fun createTransition(pushData: PushData) {
            val pushCache = PushCache()
            val registrationId = pushCache.getRegistrationIdFromPref()
            val transition = registrationId?.let { service.createTransition(it, pushData) }
            //Log.d(TAG, "createTransition: = $transition")
        }
    }
}