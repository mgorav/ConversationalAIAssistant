package com.gonnect.helpme.helpme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Reservation {
    private String reservationNumber;
    private LocalDate reservationFrom;
    private LocalDate reservationTo;
    private Member member;
}
