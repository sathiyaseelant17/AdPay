package com.fab.adpay.exceptionHandler;

import com.fab.adpay.model.ApiError;
import com.fab.adpay.model.ElpasoError;
import com.fab.adpay.exception.ElpasoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        LOGGER.error(request.getHeader("transactionid"), ex.getMessage(), ex);
        final ApiError apiError = new ApiError(String.valueOf(294),
                "Technical Error Occured, please contact Magnati support");
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.OK);
    }

    @ExceptionHandler(ElpasoException.class)
    public ResponseEntity<Object> handleElpasoException(ElpasoException exception) {
        LOGGER.error(exception.getTransactionId(), exception.getMessage(), exception);
        ElpasoError elpasoError = new ElpasoError();
        elpasoError.setStatusCode(String.valueOf(exception.getStatusCode()));
        elpasoError.setStatusText(exception.getStatusText());
        return new ResponseEntity<Object>(elpasoError, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(request.getHeader("transactionid"), ex.getMessage(), ex);
        final ApiError apiError = new ApiError(String.valueOf(212), "Mandatory input fields can not be empty");
        return new ResponseEntity<Object>(apiError, HttpStatus.OK);
    }

    

}
