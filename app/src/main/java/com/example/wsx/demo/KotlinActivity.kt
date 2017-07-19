package com.example.wsx.demo

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_kotlin.*
import org.jetbrains.anko.onClick

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        btn_getMetric.onClick {
            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)

            val realMetrics = DisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(realMetrics)
            }

            val text : String  =
                    "getMetrics width = ${metrics.widthPixels}, " + "height = ${metrics.heightPixels}," + "dpi = ${metrics.densityDpi}\n" +
                    "getRealMetrics width = ${realMetrics.widthPixels},"+ "height = ${realMetrics.heightPixels},"+"dpi = ${realMetrics.densityDpi}"

            tv_title.text = text
        }
    }
}
