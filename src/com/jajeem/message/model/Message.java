package com.jajeem.message.model;

public class Message {

	// properties
	private int id;
	private int fromId;
	private int toId;
	private String content;
	private String date;
	private short type;
	private byte anonymous;

	// getter & setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public byte getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(byte anonymous) {
		this.anonymous = anonymous;
	}
}
