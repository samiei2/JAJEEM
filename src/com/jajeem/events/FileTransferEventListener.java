package com.jajeem.events;

import java.util.EventListener;

public interface FileTransferEventListener extends EventListener {

	@SuppressWarnings("rawtypes")
	public void success(FileTransferObject evt, Class t);

	@SuppressWarnings("rawtypes")
	public void fail(FileTransferObject evt, Class t);

	@SuppressWarnings("rawtypes")
	public void progress(FileTransferObject evt, Class t);

	@SuppressWarnings("rawtypes")
	public void fileSendRequest(FileTransferObject evt, Class t);

	@SuppressWarnings("rawtypes")
	public void fileAcceptRequest(FileTransferObject evt, Class t);

	@SuppressWarnings("rawtypes")
	public void fileRejectRequest(FileTransferObject evt, Class t);
}
