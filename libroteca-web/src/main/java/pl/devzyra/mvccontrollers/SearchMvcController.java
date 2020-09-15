package pl.devzyra.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.devzyra.model.response.BookRest;
import pl.devzyra.services.BookService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class SearchMvcController {


    private final BookService bookService;


    public SearchMvcController(BookService bookService) {
        this.bookService = bookService;
    }

/*
    @GetMapping
    @RequestMapping
    String searchSome(Model model) {

        List<BookRest> books = bookService.findBooksByTitle("Book");

        model.addAttribute("books", books);


        return "booklist";
    }*/

    @GetMapping
    @RequestMapping("/searchMvc")
    String search(@RequestParam(name = "searchBy") String searchBy, @RequestParam String name, Model model) {

        List<BookRest> books = new ArrayList<>();

        switch (searchBy) {
            case "title":
                books = bookService.findBooksByTitle(name);
                break;
            case "author":
                books = bookService.findBooksByAuthor(name);
                break;

        }

        model.addAttribute("books", books);


        return "index";
    }


}