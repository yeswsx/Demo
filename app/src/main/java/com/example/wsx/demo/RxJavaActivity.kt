package com.example.wsx.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RxJavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java)

        Observable.just("Hello", "Hi", "Aloha")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    private val tag = "subscriber"
                    override fun onNext(s: String) {
                        Log.d(tag, "Item: " + s)
                    }

                    override fun onCompleted() {
                        Log.d(tag, "Completed!")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(tag, "Error!")
                    }
                })
    }
}
