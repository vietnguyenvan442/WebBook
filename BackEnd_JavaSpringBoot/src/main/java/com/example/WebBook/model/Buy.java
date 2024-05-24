package com.example.WebBook.model;

import java.sql.Date;

public class Buy {
	private Book book;
	private User user;
	private int soLuong;
	private Date day;
	public Buy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Buy(Book book, User user, int soLuong, Date day) {
		super();
		this.book = book;
		this.user = user;
		this.soLuong = soLuong;
		this.day = day;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	
}
