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
        log.info("Found {} books in repository", books.size());
        return buildResponse(books, CustomStatus.SUCCESS);
    }

    public CustomResponse<Book> getBookByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .map(book -> {
                    log.info("Book with title '{}' found", title);
                    return buildResponse(Stream.of(book).toList(), CustomStatus.SUCCESS);
                })
                .orElseGet(() -> {
                    log.warn("Book with title '{}' not found", title);
                    return buildResponse(List.of(), CustomStatus.NOT_FOUND);
                });
    }

    public CustomResponse<Book> addBook(Book book) {
        Book newBook = bookRepository.save(book);
        log.info("New book persisted with title='{}', author='{}'", newBook.getTitle(), newBook.getAuthor());
        return buildResponse(Stream.of(newBook).toList(), CustomStatus.SUCCESS);
    }

    private CustomResponse<Book> buildResponse(List<Book> books, CustomStatus status) {
        return new CustomResponse<>(books, status);
    }
}