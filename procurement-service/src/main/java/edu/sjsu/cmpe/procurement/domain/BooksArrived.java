/**
 * 
 */
package edu.sjsu.cmpe.procurement.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Amit
 *
 */
public class BooksArrived {
	
	@JsonProperty
	private ArrayList<Book> shipped_books = new ArrayList<Book>();

	/**
	 * @return returns: ArrayList<Book> 
	 */
	public ArrayList<Book> getShipped_books() {
		return shipped_books;
	}

	/**
	 * @param shipped_books the shipped_books to set
	 */
	public void setShipped_books(ArrayList<Book> shipped_books) {
		this.shipped_books = shipped_books;
	}
}
