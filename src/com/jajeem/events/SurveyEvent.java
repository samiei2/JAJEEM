package com.jajeem.events;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

/**
 * Created with IntelliJ IDEA.
 * User: Armin
 * Date: 5/1/13
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SurveyEvent implements Serializable{
    protected static EventListenerList listenerList = new EventListenerList();

    public void addEventListener(SurveyEventListener listener) {
        listenerList.add(SurveyEventListener.class, listener);
    }
    public void removeEventListener(SurveyEventListener listener) {
        listenerList.remove(SurveyEventListener.class, listener);
    }
    public void fireResponseEvent(SurveyResponse evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == SurveyEventListener.class) {
                ((SurveyEventListener) listeners[i+1]).questionAnswered(evt);
            }
        }
    }
    public void fireStopEvent(SurveyStop evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == SurveyEventListener.class) {
                ((SurveyEventListener) listeners[i+1]).surveyStoped(evt);
            }
        }
    }
    public void fireSurveyFinished(SurveyFinished evt){
    	Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == SurveyEventListener.class) {
                ((SurveyEventListener) listeners[i+1]).surveyFinished(evt);
            }
        }
    }
}
