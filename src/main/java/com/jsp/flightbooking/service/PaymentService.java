package com.jsp.flightbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Booking;
import com.jsp.flightbooking.entity.ModeOfPayment;
import com.jsp.flightbooking.entity.Payment;
import com.jsp.flightbooking.entity.PaymentStatus;
import com.jsp.flightbooking.exception.IdNotFoundException;
import com.jsp.flightbooking.repository.BookingRepository;
import com.jsp.flightbooking.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayments() {

		ResponseStructure<List<Payment>> res = new ResponseStructure<>();

		List<Payment> payments = paymentRepository.findAll();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("All payments fetched successfully");
		res.setData(payments);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(Integer id) {

		ResponseStructure<Payment> res = new ResponseStructure<>();

		Optional<Payment> opt = paymentRepository.findById(id);

		if (opt.isPresent()) {

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Payment fetched successfully");
			res.setData(opt.get());

			return new ResponseEntity<>(res, HttpStatus.OK);
		}

		throw new IdNotFoundException("Payment Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(
	        PaymentStatus paymentStatus) {

	    ResponseStructure<List<Payment>> res = new ResponseStructure<>();

	    List<Payment> payments = paymentRepository.findByPaymentStatus(paymentStatus);

	    if (!payments.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Payments fetched successfully");
	        res.setData(payments);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("No Payment Found With Status : " + paymentStatus);
	}
	
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(
	        Integer id, PaymentStatus paymentStatus) {

	    ResponseStructure<Payment> res = new ResponseStructure<>();

	    Optional<Payment> opt = paymentRepository.findById(id);

	    if (opt.isPresent()) {

	        Payment payment = opt.get();

	        payment.setPaymentStatus(paymentStatus);

	        paymentRepository.save(payment);

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Payment Status Updated Successfully");
	        res.setData(payment);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Payment Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByModeOfPayment(
	        ModeOfPayment modeOfPayment) {

	    ResponseStructure<List<Payment>> res = new ResponseStructure<>();

	    List<Payment> payments = paymentRepository.findByModeOfPayment(modeOfPayment);

	    if (!payments.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Payments fetched successfully");
	        res.setData(payments);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("No Payment Found With Mode : " + modeOfPayment);
	}
	
	public ResponseEntity<ResponseStructure<Double>> getTotalAmountPaidOnFlight(Integer flightId) {

	    ResponseStructure<Double> res = new ResponseStructure<>();

	    List<Booking> bookings = bookingRepository.findByFlightFlightId(flightId);

	    if (bookings.isEmpty()) {
	        throw new IdNotFoundException("No Booking Found For Flight Id : " + flightId);
	    }

	    double totalAmount = 0;

	    for (Booking booking : bookings) {

	        if (booking.getPayment() != null) {
	            totalAmount += booking.getPayment().getAmount();
	        }
	    }

	    res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Total Amount Calculated Successfully");
	    res.setData(totalAmount);

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
