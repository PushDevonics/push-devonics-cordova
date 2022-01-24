package pro.devonics.push

import android.content.Context
import android.content.Intent
import pro.devonics.push.DataHelper.Companion.createTransition
import pro.devonics.push.DataHelper.Companion.startTime
import pro.devonics.push.model.PushData
import pro.devonics.push.model.TimeData
import pro.devonics.push.network.ApiHelper
import pro.devonics.push.network.RetrofitBuilder
import java.util.*


private const val TAG = "PushDevonics"

class PushDevonics(context: Context, appId: String) {

    private val service = ApiHelper(RetrofitBuilder.apiService)

    init {
        AppContextKeeper.setContext(context)
        PushInitialization.run(appId)
        createInternalId()
        startTime()
    }

    fun sendIntent(intent: Intent) {

        if ("transition" == intent.getStringExtra("command")) {

            val bundle = intent.extras
            val pushType = bundle?.get("push_type").toString()
            val pushId = bundle?.get("push_id").toString()

            val pushData = PushData(pushType, pushId)
            createTransition(pushData)
            //Log.d(TAG, "sendIntent: pushData = $pushData")
            //Log.d(TAG, "pushType: $pushType")
            //Log.d(TAG, "pushId: $pushId")
        }
    }

    fun getInternalId(): String? {
        val pushCache = PushCache()
        return pushCache.getInternalIdFromPref()
    }

    fun startSession() {
        //Log.d(TAG, "startSession: ")
        val pushCache = PushCache()
        val registrationId = pushCache.getRegistrationIdFromPref()

        if (pushCache.getSubscribeStatusFromPref() == true) {
            val session = registrationId?.let { service.createSession(it) }
            //Log.d(TAG, "subscribeStatus = ${pushCache.getSubscribeStatusFromPref()}")

        }
    }

    fun stopSession() {
        val duration = DataHelper.getDuration()
        val pushCache = PushCache()
        val regId = pushCache.getRegistrationIdFromPref()
        if (regId != null) {
            val timeData = TimeData(duration)
            service.sendTimeStatistic(regId, timeData)
            //Log.d(TAG, "stopSession: timeData $timeData")
        }

        //Log.d(TAG, "stopSession: duration $duration")
        //Log.d(TAG, "stopSession: regId $regId")

        //Log.d(TAG, "stopSession")
    }

    fun setTags(key: String, value: String) {
        val pushCache = PushCache()
        if (key == null && value == null) {
            pushCache.saveTagKey("")
            pushCache.saveTagValue("")
        } else {
            pushCache.saveTagKey(key)
            pushCache.saveTagValue(value)
        }

        //Log.d(TAG, "setTags: $key : $value")
    }

    private fun createInternalId() {
        val pushCache = PushCache()

        var internalId = pushCache.getInternalIdFromPref()
        if (internalId == null) {
            val uuid = UUID.randomUUID()
            internalId = uuid.toString()
            pushCache.saveInternalId(internalId)

        }
        //Log.d(TAG, "createInternalId(): internalId = $internalId")
    }
}