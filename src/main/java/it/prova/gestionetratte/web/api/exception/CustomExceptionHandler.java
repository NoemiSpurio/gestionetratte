package it.prova.gestionetratte.web.api.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(AirbusNotFoundException.class)
	public ResponseEntity<Object> handleAirbusNotFoundException(AirbusNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TrattaNotFoundException.class)
	public ResponseEntity<Object> handleTrattaNotFoundException(TrattaNotFoundException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IdNotNullForInsertException.class)
	public ResponseEntity<Object> handleIdNotNullForInsertException(IdNotNullForInsertException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.UNPROCESSABLE_ENTITY);

		return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(AirbusConTratteAssociateException.class)
	public ResponseEntity<Object> handleAirbusConTratteAssociateException(AirbusConTratteAssociateException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.UNPROCESSABLE_ENTITY);

		return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(RimozioneTrattaNonAnnullataException.class)
	public ResponseEntity<Object> handleRimozioneTrattaNonAnnullataException(RimozioneTrattaNonAnnullataException ex,
			WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_ACCEPTABLE);

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(TrattaValidationException.class)
	public ResponseEntity<Object> handleValidationException(TrattaValidationException ex, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.NOT_ACCEPTABLE);

		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}
}
