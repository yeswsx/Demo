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
    var mMessage : StringBuffer = StringBuffer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus)

        btn_posting.setOnClickListener(this)
        btn_background.setOnClickListener(this)
        btn_async.setOnClickListener(this)
        btn_main.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_posting -> { post(PostingEvent()) }
            R.id.btn_background -> { post(BackgroundEvent()) }
            R.id.btn_async -> {post(AsyncEvent())}
            R.id.btn_main -> {post(MainEvent())}
        }
    }

    override fun onStart() {
        super.onStart()
        /**
         * 还没注册前先发送一个粘性广播
         */
        showMessage("注册之前post", StickyEvent())
        EventBus.getDefault().postSticky(StickyEvent())

        /**
         * 注册订阅者
         */
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        /**
         * 取消注册
         */
        EventBus.getDefault().unregister(this)
    }

    /**
     * 发布事件
     */
    private fun post(obj: kotlin.Any){
        mMessage.setLength(0)
        /**
         * UI线程发出
         */
        showMessage("post", obj)
        EventBus.getDefault().post(obj)

        /**
         * 子线程发出
         */
        Thread(Runnable {
            showMessage("post", obj)
            EventBus.getDefault().post(obj)
        }).start()
    }

    /**
     * 事件订阅
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2)
    fun onEventP2(event : MainEvent){
        showMessage("run 优先级高",event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    fun onEventP1(event : MainEvent){
        showMessage("run 优先级低", event)
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onEvent(event : PostingEvent){
        showMessage("run",event)
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onEvent(event : BackgroundEvent){
        showMessage("run",event)
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onEvent(event : AsyncEvent){
        showMessage("run",event)
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    fun onEvent(event: StickyEvent){
        showMessage( "run", event)
    }

    fun showMessage( message: String, obj: Any) {
        synchronized(this){
            mMessage.append("${message} ${obj.javaClass.simpleName} -- on: ${Thread.currentThread().name}")
            mMessage.append("\n")
        }

        if (Looper.getMainLooper() == Looper.myLooper()){
            text_info.setText(mMessage)
        }else{
            text_info.post({text_info.setText(mMessage)})
        }
    }
}
