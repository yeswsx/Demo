package com.example.wsx.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wsx.demo.event.EventBusActivity;
import com.example.wsx.demo.rxjava.RxJavaActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_rxjava_test, R.id.btn_mvp_test, R.id.btn_kotlin, R.id.btn_second_activity,
	R.id.btn_new_context_activity, R.id.btn_eventbus_activity})
    public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_rxjava_test: {
				startActivity(new Intent(this, RxJavaActivity.class));
				break;
			}
			case R.id.btn_mvp_test: {
				startActivity(new Intent(this, TestMvpActivity.class));
				break;
			}
			case R.id.btn_retrofit: {
				break;
			}
			case R.id.btn_kotlin: {
				startActivity(new Intent(this, KotlinActivity.class));
				break;
			}
			case R.id.btn_second_activity: {
				startActivity(new Intent(this, SecondActivity.class));
				break;
			}
			case R.id.btn_new_context_activity: {
				startActivity(new Intent(this, NewContextActivity.class));
				break;
			}
			case R.id.btn_eventbus_activity: {
				startActivity(new Intent(this, EventBusActivity.class));
				break;
			}
		}
	}
}
