package br.com.example.forum.Forum.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception {

    private static final long serialVersionUID = -1585382398093606918L;
    private static final String MSG_UNKOWN = "Mensagem desconhecida";
    private HttpStatus status;

    public ValidationException() {
        super(MSG_UNKOWN);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ValidationException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ValidationException(Throwable t,HttpStatus status) {
        super(t);
        this.status = status;
    }

    public ValidationException(String mensagem, Throwable t, HttpStatus status) {
        super(mensagem, t);
        this.status = status;
    }

    public ValidationException(MensagensException msg, HttpStatus status) {
        super(msg.getText());
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
