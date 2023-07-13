package dataart.workshop.bookmanager.controller;

import dataart.workshop.bookmanager.dto.AddBookRequest;
import dataart.workshop.bookmanager.dto.AddBookResponse;
import dataart.workshop.bookmanager.dto.BookDto;
import dataart.workshop.bookmanager.dto.BookOrdersDto;
import dataart.workshop.bookmanager.dto.PaginatedBookDto;
import dataart.workshop.bookmanager.dto.UpdateBookRequest;
import dataart.workshop.bookmanager.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/{bookId}")
    public BookDto getById(@PathVariable Long bookId) {
        return bookService.findByBookId(bookId);
    }

    @GetMapping
    public PaginatedBookDto getAll(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer size) {
        return bookService.findAll(page, size);
    }

    @GetMapping("/{bookId}/orders")
    public BookOrdersDto getAllOrdersForBook(@PathVariable Long bookId) {
        return bookService.getOrdersForBook(bookId);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddBookResponse add(@RequestBody AddBookRequest addBookRequest) {
        return bookService.save(addBookRequest);
    }

    @PutMapping("/{bookId}")
    public BookDto update(@PathVariable Long bookId, @RequestBody UpdateBookRequest updateBookRequest) {
        return bookService.update(bookId, updateBookRequest);
    }

    @DeleteMapping("/{bookId}")
    public void delete(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }
}
