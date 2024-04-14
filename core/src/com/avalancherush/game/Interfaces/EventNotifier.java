package com.avalancherush.game.Interfaces;

import com.avalancherush.game.Enums.EventType;
import com.badlogic.gdx.ScreenAdapter;

import java.util.List;

public abstract class EventNotifier extends ScreenAdapter {
    protected List<EventObserver> observers;
    protected void notifyObservers(List<EventObserver> observers, EventType eventType){
        for (EventObserver observer: observers
        ) {
            observer.notify(eventType);
        }
    }
}
