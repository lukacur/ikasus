package hr.fer.ika.ikasus.controller;

import hr.fer.ika.ikasus.DTO.outgoing.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Luka Ćurić
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ IllegalStateException.class })
    public ResponseEntity<CommonResponse> handleIllegalState() {
        CommonResponse errResp = new CommonResponse();
        errResp.setIsError(true);
        errResp.setError("Invalid request");

        return ResponseEntity.badRequest().body(errResp);
    }
}
