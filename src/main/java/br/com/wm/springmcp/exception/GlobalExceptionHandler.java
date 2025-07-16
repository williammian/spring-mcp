package br.com.wm.springmcp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        // Este log será gravado no arquivo de erro
        log.error("Erro não tratado capturado: {}", e.getMessage(), e);
        
        // Retorna uma resposta limpa para o cliente
        return ResponseEntity.internalServerError().body("Erro interno do servidor");
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.error("Erro de runtime: {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body("Erro na requisição");
    }
	
}
