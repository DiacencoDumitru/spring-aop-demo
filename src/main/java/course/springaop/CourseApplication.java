package course.springaop;

import course.springaop.entity.Book;
import course.springaop.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseApplication implements CommandLineRunner {

    private final BookRepository bookRepository;

    public CourseApplication(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book("War and Peace", "Leo Tolstoy");
        Book book2 = new Book("The Captain's Daughter", "Alexander Pushkin");

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
