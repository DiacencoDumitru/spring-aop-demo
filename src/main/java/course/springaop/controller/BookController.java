package course.springaop.controller;

import course.springaop.entity.Book;
import course.springaop.service.BookService;
import course.springaop.util.CustomResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public CustomResponse<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("books/{title}")
    public CustomResponse<Book> findBookByTitle(@PathVariable("title") String title) {
        return bookService.getBookByTitle(title);
    }

    @PostMapping("/books")
    public CustomResponse<Book> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
}