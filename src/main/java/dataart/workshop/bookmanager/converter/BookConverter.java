package dataart.workshop.bookmanager.converter;

import dataart.workshop.bookmanager.domain.Book;
import dataart.workshop.bookmanager.dto.AddBookRequest;
import dataart.workshop.bookmanager.dto.BookDto;
import dataart.workshop.bookmanager.dto.PaginatedBookDto;
import dataart.workshop.bookmanager.dto.UpdateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookConverter {

    public BookDto toBookDto(Book book) {
        BookDto bookDto = new BookDto();

        bookDto.setId(book.getBookId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());

        return bookDto;
    }

    public Book toBook(AddBookRequest addBookRequest) {
        Book book = new Book();

        book.setAuthor(addBookRequest.getAuthor());
        book.setPrice(addBookRequest.getPrice());
        book.setTitle(addBookRequest.getTitle());

        return book;
    }

    public PaginatedBookDto toPaginatedDto(Page<Book> bookPage) {
        PaginatedBookDto paginatedBookDto = new PaginatedBookDto();

        paginatedBookDto.setSize(bookPage.getSize());
        paginatedBookDto.setPage(bookPage.getNumber());
        paginatedBookDto.setTotalPages(bookPage.getTotalPages());

        List<BookDto> bookDtos = bookPage.get()
                .map(this::toBookDto)
                .toList();
        paginatedBookDto.setData(bookDtos);

        return paginatedBookDto;
    }

    public Book toBook(UpdateBookRequest updateBookRequest) {
        Book book = new Book();

        book.setAuthor(updateBookRequest.getAuthor());
        book.setPrice(updateBookRequest.getPrice());
        book.setTitle(updateBookRequest.getTitle());

        return book;
    }
}
