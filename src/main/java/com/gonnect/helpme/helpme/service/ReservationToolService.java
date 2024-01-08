package com.gonnect.helpme.helpme.service;

import com.gonnect.helpme.helpme.model.Reservation;
import com.gonnect.helpme.helpme.repository.ReservationRepository;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationToolService {

    private static final Logger log = LoggerFactory.getLogger(ReservationToolService.class);


    @Autowired
    private ReservationRepository reservationRepository;

    @Tool
    public Reservation getReservationDetails(String reservationNumber, String memberName, String memberSurname) {
        log.info("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
        log.info("║ 🛳️  Getting details for reservation {} for member {} {}", reservationNumber, memberName, memberSurname);
        log.info("╠═══════════════════════════════════════════════════════════════════════════════════════╣");

        return reservationRepository.getReservationDetails(reservationNumber, memberName, memberSurname);
    }

    @Tool
    public void cancelReservation(String reservationNumber, String memberName, String memberSurname) {
        log.info("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
        log.info("║ ✈️  Cancelling reservation {} for member {} {}", reservationNumber, memberName, memberSurname);
        log.info("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
        reservationRepository.cancelReservation(reservationNumber, memberName, memberSurname);
    }
}