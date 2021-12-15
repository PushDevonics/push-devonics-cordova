package pro.devonics.push.network

import pro.devonics.push.*
import pro.devonics.push.model.*
import pro.devonics.push.model.Tag
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //1 Получаем sender_id, который используем для инициализации fcm
    @GET(VIEW)
    fun getSenderId(@Path("app_id") app_id: String): Call<Sender>

    //2 Отсылаем запрос только при первом открытии приложения и сохраняем
    //   registration_id в кеш, получаем internal_id
    @POST(SUBSCRIBE_USER)
    fun createPush(@Body pushUser: PushUser): Call<Status>

    //3 Отсылаем каждый раз при открытии приложения
    @GET(SESSION)
    fun createSession(@Path("registration_id") registrationId: String): Call<Status>//: Call<PushUser>

    //4 Отсылаем при обновление fcm токена
    @PUT(UPDATE_USER)
    fun updateUser(@Body pushInstance: PushInstance): Call<PushInstance>//Response<Status>

    // Отсылаем свои теги
    @POST(SAVE_TAG)
    fun saveCustomParams(@Body tag: Tag): Call<Tag>

    // Отсылаем продолжительность работы
    @POST(DURATION)
    fun sendDuration(@Body timeData: TimeData): Call<TimeData>

    // Отсылаем переход по пушу
    @POST(TRANSITION)
    fun createTransition(
        @Path("registration_id") registrationId: String,
        @Body  pushData: PushData): Call<Status>
}