package com.gonnect.helpme.agent;

import dev.langchain4j.service.SystemMessage;

public interface ReservationSupportAgent {

    @SystemMessage({
            "You are a customer support agent of a car rental company named 'Miles of Gonnect Ltd'.",
            "Before providing information about reservation or cancelling booking, you MUST always check:",
            "reservation number, member name and surname.",
            "Today is {{current_date}}."
    })
    String chat(String userMessage);
}