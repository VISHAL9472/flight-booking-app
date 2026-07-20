package com.jsp.flightbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flightbooking.entity.ModeOfPayment;
import com.jsp.flightbooking.entity.Payment;
import com.jsp.flightbooking.entity.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	
	List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

	List<Payment> findByModeOfPayment(ModeOfPayment modeOfPayment);
}
