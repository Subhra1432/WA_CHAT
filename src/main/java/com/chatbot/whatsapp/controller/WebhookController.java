package com.chatbot.whatsapp.controller;

import com.chatbot.whatsapp.model.IncomingMessage;
import com.chatbot.whatsapp.model.OutgoingMessage;
import com.chatbot.whatsapp.service.ChatbotService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes the /webhook endpoint.
 * Simulates a WhatsApp Business API webhook receiver.
 */
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final ChatbotService chatbotService;

    public WebhookController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * POST /webhook
     *
     * Receives a simulated WhatsApp message and returns a chatbot reply.
     *
     * @param incoming the incoming message payload
     * @return ResponseEntity containing the chatbot's reply
     */
    @PostMapping
    public ResponseEntity<OutgoingMessage> receiveMessage(
            @Valid @RequestBody IncomingMessage incoming) {

        OutgoingMessage reply = chatbotService.processMessage(incoming);
        return ResponseEntity.ok(reply);
    }

    /**
     * GET /webhook
     *
     * Health-check / verification endpoint (mirrors WhatsApp webhook verification).
     */
    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(value = "hub.mode", required = false) String mode,
            @RequestParam(value = "hub.verify_token", required = false) String token,
            @RequestParam(value = "hub.challenge", required = false) String challenge) {

        logger.info("🔍 Webhook verification request — mode={}, token={}", mode, token);

        // Simple verification: if a challenge is provided, echo it back
        if (challenge != null && !challenge.isEmpty()) {
            return ResponseEntity.ok(challenge);
        }

        return ResponseEntity.ok("✅ WhatsApp Chatbot Webhook is active!");
    }
}
