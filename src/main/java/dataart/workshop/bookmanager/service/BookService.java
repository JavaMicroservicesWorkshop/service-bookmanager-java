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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private static final String CANT_FIND_BOOK = "Can't find book by id: %s";

    private final BookConverter bookConverter;
    private final BookRepository bookRepository;
    private final PageUtils pageUtils;
    private final BookServiceValidator bookServiceValidator;

    public BookDto findByBookId(String bookId) {
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

        String bookId = UUID.randomUUID().toString();
        Book book = bookConverter.toBook(addBookRequest, bookId);
        bookRepository.save(book);

        return new AddBookResponse(bookId);
    }

    @Transactional
    public BookDto update(String bookId, UpdateBookRequest updateBookRequest) {
        Book existingBook = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new BookNotFoundException(""));

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
    public void delete(String bookId) {
        bookServiceValidator.validateBookPresence(bookId);

        bookRepository.deleteByBookId(bookId);
    }
}
