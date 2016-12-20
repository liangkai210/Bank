package com.kailiang.lms.bean;

public class Book {

	private int bookId;
	private String title;
	private Integer pubId;

	public Book() {
	}

	public Book(String title) {
		this.title = title;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPubId() {
		return pubId;
	}

	public void setPubId(Integer pubId) {
		this.pubId = pubId;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", pubId=" + pubId + "]";
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

}
