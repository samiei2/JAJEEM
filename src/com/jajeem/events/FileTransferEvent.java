package com.jajeem.events;

import java.io.Serializable;

import javax.swing.event.EventListenerList;

	public class FileTransferEvent implements Serializable{
	protected static EventListenerList listenerList = new EventListenerList();

    public void addEventListener(FileTransferEventListener listener) {
        listenerList.add(FileTransferEventListener.class, listener);
    }
    public void removeEventListener(FileTransferEventListener listener) {
        listenerList.remove(FileTransferEventListener.class, listener);
    }
    public void fireSuccess(FileTransferObject evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).success(evt);
            }
        }
    }
    public void fireFailure(FileTransferObject evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fail(evt);
            }
        }
    }
	public void fireProgress(FileTransferObject evt) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).progress(evt);
            }
        }
	}
	public void fireFileSendRequest(FileTransferObject evt) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fileSendRequest(evt);
            }
        }
	}
	public void fireAcceptFileRequest(FileTransferObject evt) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fileAcceptRequest(evt);
            }
        }
	}
	public void fireRejectFileRequest(FileTransferObject evt) {
		Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i+2) {
            if (listeners[i] == FileTransferEventListener.class) {
                ((FileTransferEventListener) listeners[i+1]).fileRejectRequest(evt);
            }
        }
	}
}
