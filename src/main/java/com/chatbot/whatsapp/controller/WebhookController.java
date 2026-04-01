package com.chatbot.whatsapp.controller;

import com.chatbot.whatsapp.model.IncomingMessage;
import com.chatbot.whatsapp.model.OutgoingMessage;
import com.chatbot.whatsapp.service.ChatbotService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    private final ChatbotService chatbotService;

    public WebhookController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping(consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> receiveMessageJson(
            @Valid @RequestBody IncomingMessage incoming) {

        OutgoingMessage reply = chatbotService.processMessage(incoming);
        return ResponseEntity.ok(reply.getReply());
    }

    @PostMapping(consumes = "text/plain", produces = "text/plain")
    public ResponseEntity<String> receiveMessageText(@RequestBody String message) {

        IncomingMessage incoming = new IncomingMessage();
        incoming.setFrom("User");
        incoming.setMessage(message);
        
        OutgoingMessage reply = chatbotService.processMessage(incoming);
        return ResponseEntity.ok(reply.getReply());
    }

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(value = "hub.mode", required = false) String mode,
            @RequestParam(value = "hub.verify_token", required = false) String token,
            @RequestParam(value = "hub.challenge", required = false) String challenge) {

        logger.info("Webhook verification request - mode={}, token={}", mode, token);

        if (challenge != null && !challenge.isEmpty()) {
            return ResponseEntity.ok(challenge);
        }

        return ResponseEntity.ok("WhatsApp Chatbot Webhook is active!");
    }
}
