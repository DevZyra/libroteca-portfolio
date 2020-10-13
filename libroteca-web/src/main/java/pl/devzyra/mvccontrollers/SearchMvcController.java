package pl.devzyra.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping
public class SearchMvcController {

    private static final String INDEX = "index";
    private final BookService bookService;


    public SearchMvcController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(path = "/searchMvc")
    public String search(@RequestParam(name = "searchBy") String searchBy, @RequestParam String name, Model model) {

        Set<BookRest> books = new HashSet<>();

        if (searchBy.equals("title")) {
            books.addAll(bookService.findBooksByTitle(name));
        } else {
            books.addAll(bookService.findBooksByAuthor(name));
        }

        if (books.isEmpty()) {
            model.addAttribute("books", new BookRest(0L, "[NO RECORD/S FOUND]"));
        } else {

            model.addAttribute("books", books);
        }

        return INDEX;
    }


}
