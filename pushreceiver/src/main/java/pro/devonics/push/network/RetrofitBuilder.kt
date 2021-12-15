package pro.devonics.push.network

import pro.devonics.push.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val loggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }

    private val okhttpClient = OkHttpClient().newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}