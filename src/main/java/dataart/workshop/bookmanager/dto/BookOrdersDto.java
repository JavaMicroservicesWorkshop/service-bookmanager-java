package dataart.workshop.bookmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookOrdersDto {

    private List<OrderDto> orderDtos;
}
