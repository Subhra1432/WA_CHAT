package com.chatbot.whatsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the chatbot's reply sent back to the user.
 *
 * Example JSON:
 * {
 *   "to": "919876543210",
 *   "reply": "Hello! 👋 How can I help you today?"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingMessage {

    private String to;
    private String reply;
}
