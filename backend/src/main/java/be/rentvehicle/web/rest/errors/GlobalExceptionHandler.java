package be.rentvehicle.web.rest.errors;

import be.rentvehicle.service.impl.errors.EmailAlreadyUsedException;
import be.rentvehicle.service.impl.errors.UserNotFoundException;
import be.rentvehicle.service.impl.errors.UsernameAlreadyUsedException;
import be.rentvehicle.web.rest.AccountResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * GlobalExceptionHandler class to handle exceptions across the whole application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * A method to handle {@link MethodArgumentNotValidException} across the whole application.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("MethodArgumentNotValidException thrown !");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult()
                        .getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList()));
    }

    /**
     * A method to handle {@link UsernameAlreadyUsedException} across the whole application.
     */
    @ExceptionHandler({UsernameAlreadyUsedException.class})
    public ResponseEntity<Map<String, String>> usernameAlreadyUsedException(UsernameAlreadyUsedException ex) {
        log.warn("UsernameAlreadyUsedException thrown !");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Arrays.stream(ex.getMessage().split(":"))
                        .collect(toMap(str -> "message", str -> str)));
    }

    /**
     * A method to handle {@link EmailAlreadyUsedException} across the whole application.
     */
    @ExceptionHandler({EmailAlreadyUsedException.class})
    public ResponseEntity<Map<String, String>> emailAlreadyUsedException(EmailAlreadyUsedException ex) {
        log.warn("EmailAlreadyUsedException thrown !");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * A method to handle {@link UserNotFoundException} across the whole application.
     */
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Map<String, String>> userNotFoundException(UserNotFoundException ex) {
        log.warn("UserNotFoundException thrown !");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    /**
     * A method to handle {@link ConstraintViolationException} across the whole application.
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<List<String>> constraintViolationException(ConstraintViolationException ex) {
        log.warn("ConstraintViolationException thrown !");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList()));
    }
}

