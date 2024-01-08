package com.gonnect.helpme.helpme.repository;

import com.gonnect.helpme.helpme.exception.ReservationCannotBeCancelledException;
import com.gonnect.helpme.helpme.exception.ReservationNotFoundException;
import com.gonnect.helpme.helpme.model.Reservation;
import com.gonnect.helpme.helpme.model.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationRepository {

    public Reservation getReservationDetails(String reservationNumber, String memberName, String memberSurname) {
        ensureExists(reservationNumber, memberName, memberSurname);

        // Imitating retrieval from DB
        LocalDate reservationFrom = LocalDate.now().plusDays(1);
        LocalDate reservationTo = LocalDate.now().plusDays(3);
        Member member = new Member(memberName, memberSurname);
        return new Reservation(reservationNumber, reservationFrom, reservationTo, member);
    }

    public void cancelReservation(String reservationNumber, String memberName, String memberSurname) {
        ensureExists(reservationNumber, memberName, memberSurname);

        // Imitating cancellation
        throw new ReservationCannotBeCancelledException(reservationNumber);
    }

    private void ensureExists(String reservationNumber, String memberName, String memberSurname) {
        // Imitating check
        if (!(reservationNumber.equals("987-654")
                && memberName.equals("Gaurav")
                && memberSurname.equals("Malhotra"))) {
            throw new ReservationNotFoundException(reservationNumber);
        }
    }
}