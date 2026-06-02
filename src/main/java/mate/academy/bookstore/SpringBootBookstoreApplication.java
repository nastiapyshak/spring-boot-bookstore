package mate.academy.bookstore;

import java.math.BigDecimal;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootBookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("Murder on the Orient Express");
            book.setAuthor("Agatha Christie");
            book.setIsbn("123456789");
            book.setPrice(BigDecimal.valueOf(100));
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
