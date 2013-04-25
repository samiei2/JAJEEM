package com.jajeem.room.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.room.dao.h2.AttendantDAO;
import com.jajeem.room.dao.h2.RoomDAO;
import com.jajeem.room.dao.h2.SeatDAO;
import com.jajeem.room.dao.h2.SessionDAO;
import com.jajeem.room.model.Room;

public class RoomService implements IRoomService{
	private RoomDAO roomDAO;
	private AttendantDAO attendantDAO;
	private SeatDAO seatDAO;
	private SessionDAO sessionDAO;
	@Override
	public Room create(Room room) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room get(Room room) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Room room) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Room room) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Room> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

	public RoomDAO getRoomDAO() {
		return roomDAO;
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	public AttendantDAO getAttendantDAO() {
		return attendantDAO;
	}

	public void setAttendantDAO(AttendantDAO attendantDAO) {
		this.attendantDAO = attendantDAO;
	}

	public SeatDAO getSeatDAO() {
		return seatDAO;
	}

	public void setSeatDAO(SeatDAO seatDAO) {
		this.seatDAO = seatDAO;
	}

	public SessionDAO getSessionDAO() {
		return sessionDAO;
	}

	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}
	
}
