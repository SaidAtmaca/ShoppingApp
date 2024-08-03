package com.saidatmaca.shoppingapp.di

import android.app.Application
import androidx.room.Room
import com.saidatmaca.shoppingapp.BuildConfig
import com.saidatmaca.shoppingapp.core.common.Constants
import com.saidatmaca.shoppingapp.core.common.Constants.TIME_OUT_RETROFIT
import com.saidatmaca.shoppingapp.data.local.AppDatabase
import com.saidatmaca.shoppingapp.data.remote.APIService
import com.saidatmaca.shoppingapp.data.repository.AppRepositoryImpl
import com.saidatmaca.shoppingapp.domain.repository.AppRepository
import com.saidatmaca.shoppingapp.domain.use_case.ProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {



    /**------------------------------------------------------------------- APP PROVIDES  -----------------------------------------------***/
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }



    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {

        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .build()
            chain.proceed(request)
        }




        val builder =
            OkHttpClient
                .Builder()
                .readTimeout(
                    TIME_OUT_RETROFIT,
                    TimeUnit.SECONDS)
                .connectTimeout(
                TIME_OUT_RETROFIT,
                TimeUnit.SECONDS
            )
        builder.interceptors()
            .add(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: AppDatabase,
        api: APIService
    ): AppRepository {
        return AppRepositoryImpl(api, db.roomDao)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, Constants.ROOM_DB_NAME
        ).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }


    /**------------------------------------------------------------------- USECASE PROVIDES  -----------------------------------------------***/





    @Provides
    @Singleton
    fun provideProductUseCase(repository: AppRepository): ProductUseCase {
        return ProductUseCase(repository)
    }






}