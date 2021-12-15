package pro.devonics.push.model

import com.google.gson.annotations.SerializedName

data class Sender(

    @SerializedName("sender_id")
    private val senderId: String

    /*@SerializedName("sender_id")
    val senderId: String*/
) {
    fun getSenderId(): String {
        return senderId
    }
}
