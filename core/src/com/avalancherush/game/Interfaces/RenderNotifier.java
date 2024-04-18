package com.avalancherush.game.Interfaces;

import java.util.List;

public abstract class RenderNotifier extends EventNotifier {
    protected List<RenderObserver> renderObservers;
    public RenderNotifier(){
        super();
    }
    protected void notifyRenderObservers(List<RenderObserver> observers, float time){
        for (RenderObserver observer:renderObservers
        ) {observer.notifyRender(time);
        }
    }
}
