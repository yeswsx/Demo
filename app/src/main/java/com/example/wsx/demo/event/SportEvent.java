package com.example.wsx.demo.event;

/**
 * Created by shengxuan.wang on 2017/7/6.
 */

public class SportEvent implements IEvent {
    @Override
    public String execute() {
        return "运动";
    }
}
