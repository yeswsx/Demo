package com.example.wsx.demo.rxjava;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wsx on 2017/7/9.
 */

public class WeatherInfo {
	@SerializedName("city")
	public String city;

	@SerializedName("cityid")
	public String cityCode;

	@SerializedName("temp2")
	public String maxTemp;

	@SerializedName("temp1")
	public String minTemp;

	@SerializedName("weather")
	public String detail;

	@SerializedName("img1")
	public String img1;

	@SerializedName("img2")
	public String img2;

	@SerializedName("ptime")
	public String time;

	@Override
	public String toString() {
		return String.format("%s[%s] %s,最高温度：%s 最低温度：%s 更新时间：%s",
				city, cityCode, detail, maxTemp, minTemp, time);
	}
}
