package com.example.latter.delegates.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by Gentleman on 2018/6/24.
 */

public class EventManager {

    private static final HashMap<String,Event> EVENTS = new HashMap<>();

    private EventManager(){

    }

    private static class Holder{
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance(){
        return Holder.INSTANCE;
    }

    /**
     * 添加事件
     */
    public EventManager addEvent(@NonNull String name,@NonNull Event event){
        EVENTS.put(name,event);
        return this;
    }


    /**
     * 创建事件
     */
    public Event createEvent(@NonNull String action){
        Event event = EVENTS.get(action);
        if (event==null){
            return new UndefineEvent();
        }
        return event;
    }
}
