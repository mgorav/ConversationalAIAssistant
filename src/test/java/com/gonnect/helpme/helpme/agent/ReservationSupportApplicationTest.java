package com.gonnect.helpme.helpme.agent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootTest
class ReservationSupportApplicationTest {
    private static final Logger log = LoggerFactory.getLogger(ReservationSupportApplicationTest.class);


    @Autowired
    ReservationSupportAgent agent;

    @Test
    void should_provide_booking_details_and_explain_why_cancellation_is_not_possible() {

        // Note: To minimize AI text generation risks in this case, it is advised to
        // employ a GPT-4 series model instead of GPT-3.5-turbo. Our tests have indicated
        // higher chances of GPT-3.5-turbo accidentally specifying false personal
        // details not found in the source data.

        converse(agent, "Hi, I forgot when my booking is.");
        converse(agent, "987-654");
        converse(agent, "I'm sorry I'm so distracted today. Gaurav Malhotra.");
        converse(agent, "My bad, it's 987-654");

        // This code snippet demonstrates automated retrieval and integration of contextual cancellation rules.
        // The AI assistant tries cancelling the reservation per that policy, but is unable to complete that action.
        // It then elucidates why said cancellation cannot occur given those governing terms and conditions.
        //
        //In this manner, real-world constraints are programmatically supplied to temper ungrounded assumptions.
        // The result aims to promote compliant, context-aware behavior from the AI.

        converse(agent, "My plans have changed, can I cancel my reservation?");
    }

    private static void converse(ReservationSupportAgent agent, String userMessage) {
        log.info("╔═══════════════════════════════════════════════════════════════════════════════════════╗");

        log.info("║ [👤 User]: {}", userMessage);
        // Get agent's response
        String agentAnswer = agent.chat(userMessage);
        log.info("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
        // Agent response
        log.info("║ [👨‍💻 Agent]: {}", agentAnswer);
        log.info("╚═══════════════════════════════════════════════════════════════════════════════════════╝");
    }
}