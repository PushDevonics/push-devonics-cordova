package pro.devonics.push.model

import com.google.gson.annotations.SerializedName

data class PushData(
    @SerializedName("push_type")
    val pushType: String,
    @SerializedName("push_id")
    val pushId: String
) /*{
    //@JvmName("getPushId1")
    fun getPushId(): String {
        return pushId
    }

    //@JvmName("getPushType1")
    fun getPushType(): String {
        return pushType
    }
}
*/