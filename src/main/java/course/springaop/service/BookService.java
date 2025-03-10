package course.springaop.service;

import course.springaop.entity.Book;
import course.springaop.repository.BookRepository;
import course.springaop.util.CustomResponse;
import course.springaop.util.CustomStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public CustomResponse<Book> getAll() {
        List<Book> books = bookRepository.findAll();

        return new CustomResponse<>(books, CustomStatus.SUCCESS);
    }

    public CustomResponse<Book> getBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title).orElseThrow();

        return new CustomResponse<>(Stream.of(book).toList(), CustomStatus.SUCCESS);
    }

    public CustomResponse<Book> addBook(Book book) {
        Book newBook = bookRepository.save(book);

        return new CustomResponse<>(Stream.of(newBook).toList(), CustomStatus.SUCCESS);
    }
}