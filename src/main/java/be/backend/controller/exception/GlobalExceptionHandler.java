package be.backend.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex, HttpServletRequest req) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("path", req.getRequestURI());
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        // optioneel: log ex stacktrace
        ex.printStackTrace();
        return ResponseEntity.status(500).body(body);
    }
}
