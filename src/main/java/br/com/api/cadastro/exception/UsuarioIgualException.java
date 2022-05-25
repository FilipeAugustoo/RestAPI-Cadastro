package br.com.api.cadastro.exception;

public class UsuarioIgualException extends RuntimeException {
    
    public UsuarioIgualException(String message) {
        super(message);
    }

    public UsuarioIgualException(String message, Throwable cause) {
		super(message, cause);
	}
}
