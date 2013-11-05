package edu.sjsu.cmpe.library.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import edu.sjsu.cmpe.library.config.ConfigElements;
import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Book.Status;
import edu.sjsu.cmpe.library.dto.StompDto;
import freemarker.template.Configuration;

public class BookRepository implements BookRepositoryInterface {
    /** In-memory map to store books. (Key, Value) -> (ISBN, Book) */
    //private static ConcurrentHashMap<Long, Book> bookInMemoryMap;
	private static ConcurrentHashMap<Long, Book> bookInMemoryMap = new ConcurrentHashMap<Long, Book>();
    /** Never access this key directly; instead use generateISBNKey() */
    private static long isbnKey=2;

    public BookRepository() {
	bookInMemoryMap = seedData();
    }

    public ConcurrentHashMap<Long, Book> seedData(){
	ConcurrentHashMap<Long, Book> bookMap = new ConcurrentHashMap<Long, Book>();
	Book book = new Book();
	book.setIsbn(1);
	book.setCategory("computer");
	book.setTitle("Java Concurrency in Practice");
	try {
	    book.setCoverimage(new URL("http://goo.gl/N96GJN"));
	} catch (MalformedURLException e) {
	    // eat the exception
	}
	bookMap.put(book.getIsbn(), book);

	book = new Book();
	book.setIsbn(2);
	book.setCategory("computer");
	book.setTitle("Restful Web Services");
	try {
	    book.setCoverimage(new URL("http://goo.gl/ZGmzoJ"));
	} catch (MalformedURLException e) {
	    // eat the exception
	}
	bookMap.put(book.getIsbn(), book);

	return bookMap;
    }

    /**
     * This should be called if and only if you are adding new books to the
     * repository.
     * 
     * @return a new incremental ISBN number
     */
    private final Long generateISBNKey() {
	// increment existing isbnKey and return the new value
	return Long.valueOf(++isbnKey);
    }

    /**
     * This will auto-generate unique ISBN for new books.
     */
    @Override
    public Book saveBook(Book newBook) {
	checkNotNull(newBook, "newBook instance must not be null");
	// Generate new ISBN
	Long isbn = generateISBNKey();
	newBook.setIsbn(isbn);
	// TODO: create and associate other fields such as author

	// Finally, save the new book into the map
	bookInMemoryMap.putIfAbsent(isbn, newBook);

	return newBook;
    }

    /**
     * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#getBookByISBN(java.lang.Long)
     */
    @Override
    public Book getBookByISBN(Long isbn) {
	checkArgument(isbn > 0,
		"ISBN was %s but expected greater than zero value", isbn);
	return bookInMemoryMap.get(isbn);
    }

    @Override
    public List<Book> getAllBooks() {
	return new ArrayList<Book>(bookInMemoryMap.values());
    }

    /*
     * Delete a book from the map by the isbn. If the given ISBN was invalid, do
     * nothing.
     * 
     * @see
     * edu.sjsu.cmpe.library.repository.BookRepositoryInterface#delete(java.
     * lang.Long)
     */
    @Override
    public void delete(Long isbn) {
	bookInMemoryMap.remove(isbn);
    }

	/* 
	 * receive books from the topic and add them to repository
	 * (non-Javadoc)
	 * @see edu.sjsu.cmpe.library.repository.BookRepositoryInterface#receiveBooks(long)
	 */
	@Override
	public void receiveBooks(String message) {
		//ArrayList<String> messages = new ArrayList<String>(); //to store messages coming from the topics 
		System.out.println(".........Book Received........");
		
		/*StompDto stompDto = new StompDto();
		messages = stompDto.receiveBooks();*/
		long isbn;
		//for(String message:messages){
			isbn = getIsbn(message);
			if(bookInMemoryMap.containsKey(isbn)){
				bookInMemoryMap.get(isbn).setStatus(Status.available);;
				System.out.println("Staus changed(Isbn): "+isbn);
			}
			else{
				Book newBook = new Book();
				newBook = createNewBook(message);
				bookInMemoryMap.put(isbn, newBook);
				System.out.println("New Book Added with isbn:"+newBook.getIsbn());
			}
		//}
	}

	private Book createNewBook(String message) {
		// TODO Auto-generated method stub
		String[] values = message.split(":");
		long isbn = Long.valueOf(values[0]);
		String title = values[1].substring(1,values[1].length()-1);
		String category = values[2].substring(1,values[2].length()-1);
		URL coverImage=null;
		String http=null;
		String fullUrl=null;
		try {
			http = values[3].substring(1,values[3].length());
			fullUrl = http +":"+ values[4].substring(0, values[4].length()-1);
			System.out.println("Full url is now: "+fullUrl);
			coverImage = new URL(fullUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Book to be saved -- Isbn:"+isbn+"  "+"title:"+title+"  category:"+category+"  coverimage:"+coverImage);
		Book newBook = new Book();
		newBook.setIsbn(isbn);
		newBook.setTitle(title);
		newBook.setCategory(category);
		newBook.setCoverimage(coverImage);
		return newBook;
	}

	private long getIsbn(String message) {
		// TODO Auto-generated method stub
		int index = message.indexOf(':');
		String num = message.substring(0, index);
		long isbn = Long.valueOf(num);
		return isbn;
	}

	/**
	 * @param messages
	 * @return List of isbns from the given string list
	 */
	private ArrayList<Long> getbookIsbns(ArrayList<String> messages) {
		// TODO Auto-generated method stub
		ArrayList<Long> allIsbns = new ArrayList<Long>();
		long isbn;
		int index;
		String num;
		for(String message:messages){
			index = message.indexOf(':');
			num = message.substring(0, index);
			isbn = Long.valueOf(num);
			System.out.println("isbn is:"+isbn);
			allIsbns.add(isbn);
		}
		return allIsbns;
	}
}
