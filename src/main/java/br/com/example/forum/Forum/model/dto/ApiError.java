package br.com.example.forum.Forum.model.dto;

import br.com.example.forum.Forum.exception.ValidationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String debugMessage;
    private String path;
    private UUID uniqueId = UUID.randomUUID();

    public ApiError() {
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status.value();
        this.message = message;
        this.error =  ex.getCause().getLocalizedMessage();
        this.debugMessage =  ex.getCause().getMessage();
    }
    public ApiError(ValidationException ex, String path) {
        this.status = ex.getStatus().value();
        this.path = path;
        this.message = ex.getLocalizedMessage();
        this.error = ex.getCause().getLocalizedMessage();
        this.debugMessage = ex.getCause().getMessage();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
