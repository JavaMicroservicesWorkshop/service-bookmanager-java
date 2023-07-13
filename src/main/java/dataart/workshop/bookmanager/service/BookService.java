package dataart.workshop.bookmanager.service;

import dataart.workshop.bookmanager.converter.BookConverter;
import dataart.workshop.bookmanager.domain.Book;
import dataart.workshop.bookmanager.dto.AddBookRequest;
import dataart.workshop.bookmanager.dto.AddBookResponse;
import dataart.workshop.bookmanager.dto.BookDto;
import dataart.workshop.bookmanager.dto.PaginatedBookDto;
import dataart.workshop.bookmanager.dto.UpdateBookRequest;
import dataart.workshop.bookmanager.exception.BookNotFoundException;
import dataart.workshop.bookmanager.repository.BookRepository;
import dataart.workshop.bookmanager.utils.PageUtils;
import dataart.workshop.bookmanager.validator.BookServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private static final String CANT_FIND_BOOK = "Can't find book by id: %s";

    private final BookConverter bookConverter;
    private final BookRepository bookRepository;
    private final PageUtils pageUtils;
    private final BookServiceValidator bookServiceValidator;

    public BookDto findByBookId(Long bookId) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException(CANT_FIND_BOOK.formatted(bookId)));

        return bookConverter.toBookDto(book);
    }

    public PaginatedBookDto findAll(Integer page, Integer size) {
        Pageable pageable = pageUtils.adjustPageable(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookConverter.toPaginatedDto(bookPage);
    }

    public AddBookResponse save(AddBookRequest addBookRequest) {
        bookServiceValidator.validateBookAbsence(addBookRequest.getTitle());

        Book book = bookConverter.toBook(addBookRequest);
        Book savedBook = bookRepository.save(book);

        logger.info("New book is added. Book id is: " + savedBook.getBookId());

        return new AddBookResponse(savedBook.getBookId());
    }

    @Transactional
    public BookDto update(Long bookId, UpdateBookRequest updateBookRequest) {
        Book existingBook = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " is not found"));

        Book updatedBook = bookConverter.toBook(updateBookRequest);
        update(existingBook, updatedBook);

        return bookConverter.toBookDto(existingBook);
    }

    private void update(Book destination, Book source) {
        destination.setTitle(source.getTitle());
        destination.setAuthor(source.getAuthor());
        destination.setPrice(source.getPrice());
    }

    @Transactional
    public void delete(Long bookId) {
        bookServiceValidator.validateBookPresence(bookId);

        bookRepository.deleteByBookId(bookId);

        logger.info("Book " + bookId + " is deleted");
    }
}
