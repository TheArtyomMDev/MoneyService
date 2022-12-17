package gg.onlineja.onlinecom.di

import android.content.Context
import gg.onlineja.onlinecom.data.APIService
import gg.onlineja.onlinecom.data.subs.GetSubs
import gg.onlineja.onlinecom.utils.Constants
import gg.onlineja.onlinecom.utils.network.converter.MyCustomConverter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


val networkModule = module {

    fun provideRetrofit(context: Context): Retrofit {

        val interceptor2 = HttpLoggingInterceptor()

        interceptor2.level =
            HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor2)
            .build()


        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MyCustomConverter.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

            .client(client)
            .build()
    }
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
    single {
        provideRetrofit(get())
    }
    single {
        provideAPIService(get())
    }
    single {
        GetSubs(get(), get(), get())
    }
}