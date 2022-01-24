package pro.devonics.push.model

import com.google.gson.annotations.SerializedName

data class PushData(
    @SerializedName("pushable_type")
    val pushType: String,
    @SerializedName("pushable_id")
    val pushId: String
)