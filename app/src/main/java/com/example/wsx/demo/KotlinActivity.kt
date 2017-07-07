package com.example.wsx.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jetbrains.anko.onClick

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        tv_title.text = "kotlin activity"
        tv_title.onClick {
            tv_title.text = combine(1,3).toString()
        }
    }

    fun combine(a:Int, b:Int) : Int ? = a+b
}
