package com.avalancherush.game.Interfaces;

import com.avalancherush.game.Enums.EventType;

public interface EventObserver {
    void notify(EventType eventType);
}
