package com.jsp.flightbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flightbooking.entity.ModeOfPayment;
import com.jsp.flightbooking.entity.PaymentStatus;
import com.jsp.flightbooking.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping
	public ResponseEntity<?> getAllPayments() {
		return paymentService.getAllPayments();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPaymentById(@PathVariable Integer id) {
		return paymentService.getPaymentById(id);
	}
	
	@GetMapping("/status")
	public ResponseEntity<?> getPaymentByStatus(
	        @RequestParam PaymentStatus paymentStatus) {

	    return paymentService.getPaymentByStatus(paymentStatus);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePaymentStatus(
	        @PathVariable Integer id,
	        @RequestParam PaymentStatus paymentStatus) {

	    return paymentService.updatePaymentStatus(id, paymentStatus);
	}
	
	@GetMapping("/mode")
	public ResponseEntity<?> getPaymentByModeOfPayment(
	        @RequestParam ModeOfPayment modeOfPayment) {

	    return paymentService.getPaymentByModeOfPayment(modeOfPayment);
	}
	
	@GetMapping("/flight/{flightId}/total-amount")
	public ResponseEntity<?> getTotalAmountPaidOnFlight(
	        @PathVariable Integer flightId) {

	    return paymentService.getTotalAmountPaidOnFlight(flightId);
	}

}
