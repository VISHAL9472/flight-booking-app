package com.jsp.flightbooking.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer paymentId;
	
	private double amount;
	
	@CreationTimestamp
	private LocalDateTime paymentDateTime;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	@Enumerated(EnumType.STRING)
	private ModeOfPayment modeOfPayment;
	
	@OneToOne
	@JoinColumn
	@JsonIgnore
	private Booking booking;

}
