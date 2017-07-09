package com.example.wsx.demo.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wsx.demo.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import rx.functions.Action1;
import rx.functions.Func1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
	final String TAG = "RxJavaActivity";
	ViewGroup container;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java);
		ButterKnife.bind(this);

		findViewById(R.id.btn_image).setOnClickListener(this);
		container = (ViewGroup) findViewById(R.id.container);
	}

	@OnClick({R.id.btn_image, R.id.btn_weather})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_image:
				loadImage();
				break;
			case R.id.btn_weather:
				queryWeather();
				break;
		}

	}

	private void loadImage() {
		String[] urls = new String[]{"http://img05.tooopen.com/images/20141020/sy_73102698792.jpg",
				"http://pic1.win4000.com/wallpaper/8/546b168dc5659.jpg"};

		Observable.from(urls)
				.flatMap(new Func1<String, Observable<String>>() {
					@Override
					public Observable<String> call(String s) {
						return Observable.from(new String[]{s});
					}
				})
				.filter(new Func1<String, Boolean>() {
					@Override
					public Boolean call(String s) {
						return s.endsWith(".jpg");
					}
				})
				.map(new Func1<String, Bitmap>() {
					@Override
					public Bitmap call(String s) {
						try {
							return Glide.with(RxJavaActivity.this).load(s).asBitmap()
									.into(200, 200).get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						return null;
					}
				})
				.map(new Func1<Bitmap, ImageView>() {
					@Override
					public ImageView call(Bitmap bitmap) {
						ImageView view = new ImageView(RxJavaActivity.this);
						view.setImageBitmap(bitmap);
						return view;
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<ImageView>() {
					@Override
					public void call(ImageView imageView) {
						container.addView(imageView);
					}
				});
	}

	private void queryWeather() {
		final String sites = " http://www.weather.com.cn/data/cityinfo/";
		final String[] cityCodes = {
				"101230201", //厦门
				"101230101", //福州
				"101010100", //北京
				"101020100"  //上海
		};

		Observable.from(cityCodes)
				.map(new Func1<String, String>() {//城市码转成url
					@Override
					public String call(String s) {
						Log.i(TAG, "covert url,thread:"+Thread.currentThread().getName());
						return sites + s + ".html";
					}
				})
				.map(new Func1<String, String>() {//网络请求天气数据
					@Override
					public String call(String url) {
						Log.i(TAG, "request, thread:"+Thread.currentThread().getName());
						return requestServerResult(url);
					}
				})
				.observeOn(Schedulers.computation())//切换为计算线程
				.flatMap(new Func1<String, Observable<WeatherInfo>>() {//解析数据
					@Override
					public Observable<WeatherInfo> call(String content) {
						Log.i(TAG, "parse, thread:"+Thread.currentThread().getName());
						WeatherInfo weatherInfo = json2Weather(content);
						return Observable.just(weatherInfo);
					}
				})
				.filter(new Func1<WeatherInfo, Boolean>() {//筛选出福建城市
					@Override
					public Boolean call(WeatherInfo weatherInfo) {
						Log.i(TAG, "filter, thread:"+Thread.currentThread().getName());
						return weatherInfo.cityCode.startsWith("101230");
					}
				})
				.subscribeOn(Schedulers.newThread())//在后台订阅
				.observeOn(AndroidSchedulers.mainThread())//在主线程更新
				.subscribe(new Action1<WeatherInfo>() {
					@Override
					public void call(WeatherInfo weatherInfo) {
						TextView textView = new TextView(RxJavaActivity.this);
						textView.setText(weatherInfo.toString());
						container.addView(textView);
					}
				});
	}

	private static String requestServerResult(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");//使用GET方法获取
			conn.setConnectTimeout(5000);//设置连接的最长时间
			InputStream is = null;//获取连接的输入流
			try {
				is = conn.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			int len = 0;
			byte[] buf = new byte[1024];

			while ((len = is.read(buf)) != -1) {
				stream.write(buf, 0, len);
			}

			return new String(stream.toByteArray(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static WeatherInfo json2Weather(String content){
		try {
			JSONTokener jsonParser = new JSONTokener(content);
			JSONObject object = (JSONObject) jsonParser.nextValue();
			content = object.getString("weatherinfo");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		Gson gson = new GsonBuilder().create();
		WeatherInfo weatherInfo = gson.fromJson(content, WeatherInfo.class);
		return weatherInfo;
	}
}
