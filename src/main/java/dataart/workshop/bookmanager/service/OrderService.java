package dataart.workshop.bookmanager.service;

import dataart.workshop.bookmanager.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String ORDER_CONTROLLER_URL = "http://localhost:8083/api/v1/orders/";

    private final RestTemplate restTemplate;

    public OrderDto getOrdersForBook(Long bookId) {
        String resourceUrl = ORDER_CONTROLLER_URL + bookId;
        ResponseEntity<OrderDto> response = restTemplate.getForEntity(resourceUrl, OrderDto.class);

        return response.getBody();
    }
}
