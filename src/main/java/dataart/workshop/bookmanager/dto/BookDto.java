package dataart.workshop.bookmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
}
