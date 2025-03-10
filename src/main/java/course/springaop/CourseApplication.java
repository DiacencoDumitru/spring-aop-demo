package course.springaop;

import course.springaop.entity.Book;
import course.springaop.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseApplication implements CommandLineRunner {

    // заинжектили для взаимодействия с БД
    private final BookRepository bookRepository;

    public CourseApplication(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }

    // инициализируем пару книг для БД
    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book("Война и Мир", "Лев Николаевич Толстой");
        Book book2 = new Book("Капитанская дочка", "Александр Сергеевич Пушкин");

        // добавим эти книги в БД
        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
