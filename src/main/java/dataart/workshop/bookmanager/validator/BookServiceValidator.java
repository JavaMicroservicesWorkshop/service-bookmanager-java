package dataart.workshop.bookmanager.validator;

import dataart.workshop.bookmanager.exception.BookAlreadyExistsException;
import dataart.workshop.bookmanager.exception.BookNotFoundException;
import dataart.workshop.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookServiceValidator {

    private final BookRepository bookRepository;

    public void validateBookAbsence(String title) {
        if (bookRepository.existsByTitle(title)) {
            throw new BookAlreadyExistsException("Book with title: %s already exists".formatted(title));
        }
    }

    public void validateBookPresence(String bookId) {
        if (!bookRepository.existsByBookId(bookId)) {
            throw new BookNotFoundException("Can't find book by id: %s".formatted(bookId));
        }
    }
}
