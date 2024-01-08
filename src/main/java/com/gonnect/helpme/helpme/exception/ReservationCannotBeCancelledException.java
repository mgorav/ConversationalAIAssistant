package com.gonnect.helpme.helpme.exception;

public class ReservationCannotBeCancelledException extends RuntimeException {

    public ReservationCannotBeCancelledException(String reservationNumber) {
        super("Reservation " + reservationNumber + " cannot be canceled");
    }
}