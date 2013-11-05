package edu.sjsu.cmpe.procurement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Book {
    @JsonProperty
	private long isbn;
    
    @JsonProperty
    private String title;

    @JsonProperty
    private String category;
    
    @JsonProperty
    private String coverimage;
    // add more fields here

    /**
     * @return the isbn
     */
    
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the coverimage
	 */
	public String getCoverimage() {
		return coverimage;
	}

	/**
	 * @param coverimage the coverimage to set
	 */
	public void setCoverimage(String coverimage) {
		this.coverimage = coverimage;
	}
}
