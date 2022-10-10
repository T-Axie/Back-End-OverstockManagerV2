package digitalcity.demeyert.overstockmanager.controller;

import digitalcity.demeyert.overstockmanager.exception.ElementNotFoundException;
import digitalcity.demeyert.overstockmanager.model.dto.ErrorDTO;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(ElementNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(generateErrorResponse(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorDTO> handleException(FileUploadException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(generateErrorResponse(HttpStatus.NOT_FOUND, ex));
    }

    private ErrorDTO generateErrorResponse(HttpStatus status, Exception ex){
        return ErrorDTO.builder()
                .message(ex.getMessage())
                .receivedAt( LocalDateTime.now() )
                .status( status.value() )
                .build();
    }
}
