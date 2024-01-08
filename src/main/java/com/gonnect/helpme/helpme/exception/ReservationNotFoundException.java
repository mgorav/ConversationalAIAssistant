package com.gonnect.helpme.helpme.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String reservationNumber) {
        super("Reservation " + reservationNumber + " not found");
    }
}