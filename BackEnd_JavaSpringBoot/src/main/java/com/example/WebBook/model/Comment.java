package com.example.WebBook.model;

public class Comment {
	private int id;
	private String content;
	private User user;
	private Book book;
	private int star;
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(int id, String content, User user, Book book, int star) {
		super();
		this.id = id;
		this.content = content;
		this.user = user;
		this.book = book;
		this.star = star;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	
	
	
}
