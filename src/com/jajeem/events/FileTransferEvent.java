package com.jajeem.events;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

public class FileTransferEvent implements Serializable{
	protected static EventListenerList listenerList = new EventListenerList();

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addEventListener(FileTransferEventListener listener,Class type) {
        listenerList.add(FileTransferEventListener.class,listener);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeEventListener(FileTransferEventListener listener,Class type) {
        listenerList.remove(FileTransferEventListener.class, listener);
    }
    public void fireSuccess(FileTransferObject evt,Class t) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).success(evt,t);
            }
        }
    }
    public void fireFailure(FileTransferObject evt,Class t) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fail(evt,t);
            }
        }
    }
	public void fireProgress(FileTransferObject evt,Class t) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
            	((FileTransferEventListener) listeners[i+1]).progress(evt,t);
            }
        }
	}
	public void fireFileSendRequest(FileTransferObject evt,Class t) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fileSendRequest(evt,t);
            }
        }
	}
	public void fireAcceptFileRequest(FileTransferObject evt,Class t) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fileAcceptRequest(evt,t);
            }
        }
	}
	public void fireRejectFileRequest(FileTransferObject evt,Class t) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fileRejectRequest(evt,t);
            }
        }
	}
}
