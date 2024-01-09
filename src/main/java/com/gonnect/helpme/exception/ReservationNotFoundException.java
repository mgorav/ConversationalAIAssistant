package com.gonnect.helpme.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String reservationNumber) {
        super("Reservation " + reservationNumber + " not found");
    }
}