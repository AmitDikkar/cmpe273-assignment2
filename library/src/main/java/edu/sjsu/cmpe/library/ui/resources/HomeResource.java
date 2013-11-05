package edu.sjsu.cmpe.library.ui.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cmpe.library.repository.BookRepository;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.library.ui.views.HomeView;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {
    private final BookRepository bookRepository;

    public HomeResource(BookRepository bookRepository) {
	this.bookRepository = bookRepository;
    }

    @GET
    public HomeView getHome() {
	return new HomeView(bookRepository.getAllBooks());
    }
}
