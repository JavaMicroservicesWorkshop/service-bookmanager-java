package dataart.workshop.bookmanager.service;

import dataart.workshop.bookmanager.dto.BookOrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());
    private static final String ORDER_CONTROLLER_URL = "http://localhost:8083/api/v1/orders/book/";

    private final RestTemplate restTemplate;

    public BookOrdersDto getOrdersForBook(Long bookId) {
        String resourceUrl = ORDER_CONTROLLER_URL + bookId;
        ResponseEntity<BookOrdersDto> response = restTemplate.getForEntity(resourceUrl, BookOrdersDto.class);

        logger.info("Got list of orders for book " + bookId + " from Order Controller service");

        return response.getBody();
    }
}
