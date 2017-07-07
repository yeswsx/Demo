package com.example.wsx.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wsx.demo.event.EventBusActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_rxjava_test.setOnClickListener(this)
        btn_mvp_test.setOnClickListener(this)
        btn_kotlin.setOnClickListener(this)
        btn_second_activity.setOnClickListener(this)
        btn_new_context_activity.setOnClickListener(this)
        btn_eventbus_activity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_rxjava_test -> startActivity(Intent(this, RxJavaActivity::class.java))
            R.id.btn_mvp_test -> startActivity(Intent(this, TestMvpActivity::class.java))
            R.id.btn_retrofit -> {}
            R.id.btn_kotlin -> startActivity(Intent(this, KotlinActivity::class.java))
            R.id.btn_second_activity -> startActivity(Intent(this, SecondActivity::class.java))
            R.id.btn_new_context_activity -> startActivity(Intent(this, NewContextActivity::class.java))
            R.id.btn_eventbus_activity -> startActivity(Intent(this, EventBusActivity::class.java))
        }
    }
}
