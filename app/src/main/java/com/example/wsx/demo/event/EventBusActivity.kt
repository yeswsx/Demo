package com.example.wsx.demo.event

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import com.example.wsx.demo.R
import kotlinx.android.synthetic.main.activity_event_bus.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusActivity : AppCompatActivity(), View.OnClickListener {
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

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_eat -> {EventBus.getDefault().post(EatEvent())}
            R.id.btn_sleep -> {EventBus.getDefault().post(SleepEvent())}
            R.id.btn_sport -> {EventBus.getDefault().post(SportEvent())}
            R.id.btn_work -> {EventBus.getDefault().post(WorkEvent())}
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onEvent(event : EatEvent){
        showToast(event.execute() +", on thread:"+Thread.currentThread().name);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event : SleepEvent){
        showToast(event.execute() +", on thread:"+Thread.currentThread().name);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 10)
    fun onEventAm(event : WorkEvent){
        showToast(event.execute() +" am, on thread:"+Thread.currentThread().name);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 9)
    fun onEventPm(event : WorkEvent){
        showToast(event.execute() +" pm, on thread:"+Thread.currentThread().name);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun event(event : SportEvent){
        showToast(event.execute() +", on thread:"+Thread.currentThread().name);
    }

    fun showToast(message: String) {
        val msg = "${text_info.text} \n ${message}"
        if (Looper.getMainLooper() == Looper.myLooper()){
            text_info.setText(msg)
        }else{
            text_info.post({text_info.setText(msg)})
        }
    }
}
