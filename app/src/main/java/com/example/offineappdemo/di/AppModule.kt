package com.example.offineappdemo.di

import android.app.Application
import android.content.Context
import com.example.offineappdemo.BuildConfig
import com.example.offineappdemo.data.CommentDao
import com.example.offineappdemo.data.OfflineRoomDatabase
import com.example.offineappdemo.domain.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): OfflineRoomDatabase {
        return OfflineRoomDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideCommentDao(offlineRoomDatabase: OfflineRoomDatabase): CommentDao {
        return offlineRoomDatabase.commentDao()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(Constants.DEFAULT_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(Constants.DEFAULT_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(Constants.DEFAULT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .setPrettyPrinting()
                .create()
    }
}