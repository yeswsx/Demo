package com.example.wsx.demo.event

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wsx.demo.R
import kotlinx.android.synthetic.main.activity_event_bus.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_eat -> {EventBus.getDefault().post(EventEat())}
            R.id.btn_sleep -> {EventBus.getDefault().post(EventSleep())}
            R.id.btn_sport -> {EventBus.getDefault().post(EventSport())}
            R.id.btn_work -> {EventBus.getDefault().post(EventWork())}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus)
        btn_eat.setOnClickListener(this)
        btn_sleep.setOnClickListener(this)
        btn_sport.setOnClickListener(this)
        btn_work.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onEvent(event : EventEat){
        text_info.setText(event.javaClass.simpleName+", on thread:"+Thread.currentThread().id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event : EventSleep){
        text_info.setText(event.javaClass.simpleName+", on thread:"+Thread.currentThread().id);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event : EventWork){
        text_info.setText(event.javaClass.simpleName+", on thread:"+Thread.currentThread().id);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun event(event : EventSport){
        text_info.setText(event.javaClass.simpleName+", on thread:"+Thread.currentThread().id);
    }
}
