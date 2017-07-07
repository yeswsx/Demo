package com.example.wsx.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import java.io.IOException

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitActivity : AppCompatActivity() {

    internal val BASE_URL = "https://api.github.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)
    }
}
