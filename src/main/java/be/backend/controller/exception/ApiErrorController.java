package be.backend.controller.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Sofiane
 * 13/10/2025
 */
@RestController
public class ApiErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public ApiErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> attrs = this.errorAttributes.getErrorAttributes((WebRequest) request,
                ErrorAttributeOptions.defaults());
        Integer status = (Integer) attrs.getOrDefault("status", 500);
        return ResponseEntity.status(status).body(attrs);
    }
}
