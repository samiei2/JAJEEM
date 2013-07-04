package com.jajeem.events;

import java.util.EventListener;

public interface FileTransferEventListener extends EventListener{
	
	public void success(FileTransferObject evt, Class t);

	public void fail(FileTransferObject evt, Class t);

	public void progress(FileTransferObject evt, Class t);

	public void fileSendRequest(FileTransferObject evt, Class t);

	public void fileAcceptRequest(FileTransferObject evt, Class t);

	public void fileRejectRequest(FileTransferObject evt, Class t);
}
