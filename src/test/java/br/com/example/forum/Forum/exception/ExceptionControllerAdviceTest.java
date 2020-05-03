package br.com.example.forum.Forum.exception;

import br.com.example.forum.Forum.model.dto.ApiError;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class ExceptionControllerAdviceTest {

    private ExceptionControllerAdvice exceptionControllerAdviceUnderTest;

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        exceptionControllerAdviceUnderTest = new ExceptionControllerAdvice();
    }

    @Test
    public void testExceptionHandler() {
        // Setup
        final Exception ex = new Exception("message", new Throwable());

        request = Mockito.mock(HttpServletRequest.class);
        StringBuffer buff = new StringBuffer("http://url");
        Mockito.when(request.getRequestURL()).thenReturn(buff);
        // Run the test
        final ResponseEntity<ApiError> result = exceptionControllerAdviceUnderTest.exceptionHandler(ex, request);

        // Verify the results
    }

    @Test
    public void testExceptionHandlerValidationException0() {
        // Setup
        final Exception ex = new ValidationException();
        request = Mockito.mock(HttpServletRequest.class);
        StringBuffer buff = new StringBuffer("http://url");
        Mockito.when(request.getRequestURL()).thenReturn(buff);

        // Run the test
        final ResponseEntity<ApiError> result = exceptionControllerAdviceUnderTest.exceptionHandler(ex, request);

        // Verify the results
    }

    @Test
    public void testExceptionHandlerValidationException1() {
        // Setup
        final Exception ex = new ValidationException("message");
        request = Mockito.mock(HttpServletRequest.class);
        StringBuffer buff = new StringBuffer("http://url");
        Mockito.when(request.getRequestURL()).thenReturn(buff);

        // Run the test
        final ResponseEntity<ApiError> result = exceptionControllerAdviceUnderTest.exceptionHandler(ex, request);

        // Verify the results
    }

    @Test
    public void testExceptionHandlerValidationException2() {
        // Setup
        final Exception ex = new ValidationException("message",  new Throwable(), HttpStatus.BAD_REQUEST);
        request = Mockito.mock(HttpServletRequest.class);
        StringBuffer buff = new StringBuffer("http://url");
        Mockito.when(request.getRequestURL()).thenReturn(buff);

        // Run the test
        final ResponseEntity<ApiError> result = exceptionControllerAdviceUnderTest.exceptionHandler(ex, request);

        // Verify the results
    }

    @Test
    public void testExceptionHandlerValidationException3() {
        // Setup
        final Exception ex = new ValidationException(MensagensException.DELETE_ERROR, HttpStatus.BAD_REQUEST);
        request = Mockito.mock(HttpServletRequest.class);
        StringBuffer buff = new StringBuffer("http://url");
        Mockito.when(request.getRequestURL()).thenReturn(buff);

        // Run the test
        final ResponseEntity<ApiError> result = exceptionControllerAdviceUnderTest.exceptionHandler(ex, request);

        // Verify the results
    }

    @Test
    public void testExceptionHandlerValidationException4() {
        // Setup
        final Exception ex = new ValidationException(new Throwable(), HttpStatus.BAD_REQUEST);
        request = Mockito.mock(HttpServletRequest.class);
        StringBuffer buff = new StringBuffer("http://url");
        Mockito.when(request.getRequestURL()).thenReturn(buff);

        // Run the test
        final ResponseEntity<ApiError> result = exceptionControllerAdviceUnderTest.exceptionHandler(ex, request);

        // Verify the results
    }
}
