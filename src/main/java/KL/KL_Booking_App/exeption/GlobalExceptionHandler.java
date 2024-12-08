package KL.KL_Booking_App.exeption;

import KL.KL_Booking_App.payload.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                         WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse("FAILED", exception.getMessage(),
                new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle validation errors from MethodArgumentNotValidException
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        // Create a map to store field-specific error messages
        Map<String, String> fieldErrors = new HashMap<>();

        // Extract validation errors from the BindingResult
        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((org.springframework.validation.FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    fieldErrors.put(fieldName, errorMessage);
                    return fieldName + ": " + errorMessage; // Optionally return the field with the error message
                })
                .collect(Collectors.toList());

        // Create a custom error response
        String message = String.join(", ", errorMessages); // Optional, concatenate field errors
        ErrorResponse errorResponse = new ErrorResponse("FAILED", fieldErrors, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        // Return detailed error response with field-specific errors
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
