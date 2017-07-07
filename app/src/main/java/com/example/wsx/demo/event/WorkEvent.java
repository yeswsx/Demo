package com.example.wsx.demo.event;

/**
 * Created by shengxuan.wang on 2017/7/6.
 */

public class WorkEvent implements IEvent{
    @Override
    public String execute() {
        return "工作";
    }
}
