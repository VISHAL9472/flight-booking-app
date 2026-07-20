package com.jsp.flightbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.flightbooking.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException e){
		
		ResponseStructure<String> res = new ResponseStructure<String>();
		
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		res.setData("Failure");
		
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNRAE(NoRecordAvailableException e){

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		res.setData("Failure");

		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(BookingAlreadyCancelledException.class)
	public ResponseEntity<ResponseStructure<String>> handleACE(BookingAlreadyCancelledException e){

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		res.setData("Failure");

		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(FlightAlreadyBookedException.class)
	public ResponseEntity<ResponseStructure<String>> handleFABE(FlightAlreadyBookedException e){

	    ResponseStructure<String> res = new ResponseStructure<>();

	    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	    res.setMessage(e.getMessage());
	    res.setData("Failure");

	    return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(ContactAlreadyExistsException.class)
	public ResponseEntity<ResponseStructure<String>> handleCAEE(ContactAlreadyExistsException e){

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		res.setData("Failure");

		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(InvalidContactNumberException.class)
	public ResponseEntity<ResponseStructure<String>> handleICNE(InvalidContactNumberException e){

		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(e.getMessage());
		res.setData("Failure");

		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(NoSeatsAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNSAE(NoSeatsAvailableException e){

	    ResponseStructure<String> res = new ResponseStructure<>();

	    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	    res.setMessage(e.getMessage());
	    res.setData("Failure");

	    return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.BAD_REQUEST);

	}

}
