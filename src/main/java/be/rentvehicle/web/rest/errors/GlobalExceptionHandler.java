package be.rentvehicle.web.rest.errors;

import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("MethodArgumentNotValidException thrown !");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult()
                        .getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

    @ExceptionHandler({UsernameAlreadyUsedException.class})
    public ResponseEntity<Map<String, String>> usernameAlreadyUsedException(UsernameAlreadyUsedException ex) {
        log.warn("UsernameAlreadyUsedException thrown !");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Arrays.stream(ex.getMessage().split(":"))
                        .collect(toMap(str -> "message", str -> str)));
    }

    @ExceptionHandler({EmailAlreadyUsedException.class})
    public ResponseEntity<Map<String, String>> emailAlreadyUsedException(EmailAlreadyUsedException ex) {
        log.warn("EmailAlreadyUsedException thrown !");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }
}

