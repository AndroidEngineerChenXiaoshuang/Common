package com.jixingmao.common.event;

public class DoSomethingEvent {

    public static final int EXIT_TYPE = 1;

    private int type;

    public DoSomethingEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
