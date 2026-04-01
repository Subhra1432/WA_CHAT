package com.chatbot.whatsapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMessage {

    @NotBlank(message = "Sender phone number ('from') is required")
    private String from;

    private String to;

    private String name;

    @NotBlank(message = "Message body ('message') is required")
    private String message;
}
