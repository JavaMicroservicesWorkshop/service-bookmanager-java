package dataart.workshop.bookmanager.exception.handler;

import dataart.workshop.bookmanager.exception.BookAlreadyExistsException;
import dataart.workshop.bookmanager.exception.BookNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final String ERROR = "error";
    private static final String ERRORS = "errorMessages";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String BAD_REQUEST = "Bad request";
    private static final String CONFLICT = "Conflict";
    private static final String NOT_FOUND = "Not found";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .toList();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, BAD_REQUEST);
        map.put(ERRORS, errors);
        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleBookAlreadyExistsException(BookAlreadyExistsException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, CONFLICT);
        map.put(ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotFoundException(BookNotFoundException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, NOT_FOUND);
        map.put(ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
