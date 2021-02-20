package com.myshop.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
	private long id;
	private String title;
	private String author;
	private int numberOfPages;
	private String editedLanguage;
	private long ISBN;
	private int publishedYear;
	private BigDecimal price;
	private int stock;
	private String createdDate;
}