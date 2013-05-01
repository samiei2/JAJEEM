package com.jajeem.events;

import javax.swing.event.EventListenerList;

/**
 * Created with IntelliJ IDEA.
 * User: Armin
 * Date: 5/1/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuizEvent {
    protected EventListenerList listenerList = new EventListenerList();

    public void addEventListener(QuizEventListener listener) {
        listenerList.add(QuizEventListener.class, listener);
    }
    public void removeEventListener(QuizEventListener listener) {
        listenerList.remove(QuizEventListener.class, listener);
    }
    void fireEvent(QuizAction evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == QuizEventListener.class) {
                ((QuizEventListener) listeners[i+1]).eventOccured(evt);
            }
        }
    }
}
