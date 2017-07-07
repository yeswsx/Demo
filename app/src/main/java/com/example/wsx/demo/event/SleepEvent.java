package com.example.wsx.demo.event;

/**
 * Created by shengxuan.wang on 2017/7/6.
 */

public class SleepEvent implements IEvent {
    @Override
    public String execute() {
        return "睡觉";
    }
}
