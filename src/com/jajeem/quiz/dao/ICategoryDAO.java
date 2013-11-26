package com.jajeem.quiz.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.jajeem.quiz.model.Category;

public interface ICategoryDAO {

	Category create(Category category) throws SQLException;

	Category get(Category category) throws SQLException;

	boolean update(Category category) throws SQLException;

	boolean delete(Category category) throws SQLException;

	ArrayList<Category> list() throws SQLException;

}
