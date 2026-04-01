package com.chatbot.whatsapp.service;

import com.chatbot.whatsapp.model.IncomingMessage;
import com.chatbot.whatsapp.model.OutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    private static final DateTimeFormatter TIMESTAMP_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<String, String> PREDEFINED_REPLIES = Map.of(
            "hi",    "Hello! How can I help you today?",
            "hello", "Hi there! Welcome to our WhatsApp chatbot!",
            "bye",   "Goodbye! Have a great day!",
            "help",  "Available commands:\n• Hi - Greeting\n• Bye - Farewell\n• Help - Show this menu",
            "thanks", "You're welcome!",
            "thank you", "You're welcome! Feel free to reach out anytime!"
    );

    private static final String DEFAULT_REPLY =
            "Sorry, I didn't understand that. Type 'help' to see available commands.";

    public OutgoingMessage processMessage(IncomingMessage incoming) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
        logger.info("------------------------------------------------");
        logger.info("INCOMING MESSAGE");
        logger.info("    Timestamp : {}", timestamp);
        logger.info("    From      : {}", incoming.getFrom());
        logger.info("    Name      : {}", incoming.getName() != null ? incoming.getName() : "N/A");
        logger.info("    Message   : {}", incoming.getMessage());
        logger.info("------------------------------------------------");

        String userMessage = incoming.getMessage().trim().toLowerCase();
        String reply = PREDEFINED_REPLIES.getOrDefault(userMessage, DEFAULT_REPLY);

        String recipient = (incoming.getTo() != null && !incoming.getTo().isBlank()) 
                           ? incoming.getTo() : incoming.getFrom();

        OutgoingMessage outgoing = new OutgoingMessage(recipient, reply);

        logger.info("OUTGOING REPLY");
        logger.info("    To        : {}", outgoing.getTo());
        logger.info("    Reply     : {}", outgoing.getReply());
        logger.info("------------------------------------------------\n");

        return outgoing;
    }
}
