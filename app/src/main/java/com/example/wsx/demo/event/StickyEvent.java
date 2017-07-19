package com.example.wsx.demo.event;

/**
 * Created by wsx on 2017/7/9.
 */

public class StickyEvent {
    public String msg;
    public StickyEvent(String msg){
        this.msg = msg;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+msg+"]";
    }
}
