package dataart.workshop.bookmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedBookDto {

    private List<BookDto> data;
    private int page;
    private int totalPages;
    private int size;
}
