package com.chatbot.whatsapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an incoming WhatsApp message received at the webhook.
 *
 * Example JSON:
 * {
 *   "from": "919876543210",
 *   "name": "Subhrakanta",
 *   "message": "Hi"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMessage {

    @NotBlank(message = "Sender phone number ('from') is required")
    private String from;

    private String name;

    @NotBlank(message = "Message body ('message') is required")
    private String message;
}
