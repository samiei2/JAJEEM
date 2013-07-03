package com.jajeem.events;

import java.util.EventListener;

public interface FileTransferEventListener extends EventListener{
	
	public void success(FileTransferObject evt);

	public void fail(FileTransferObject evt);

	public void progress(FileTransferObject evt);

	public void fileSendRequest(FileTransferObject evt);

	public void fileAcceptRequest(FileTransferObject evt);

	public void fileRejectRequest(FileTransferObject evt);
}
