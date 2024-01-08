package com.gonnect.helpme.helpme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Member {
    private String name;
    private String surname;
}
