package com.chatbot.whatsapp.service;

import com.chatbot.whatsapp.model.IncomingMessage;
import com.chatbot.whatsapp.model.OutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Service that handles chatbot logic:
 * - Logs every incoming message with timestamp
 * - Matches messages against predefined replies (case-insensitive)
 * - Returns a default reply for unrecognized messages
 */
@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);

    private static final DateTimeFormatter TIMESTAMP_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Predefined reply map (keys are lowercase for case-insensitive matching).
     */
    private static final Map<String, String> PREDEFINED_REPLIES = Map.of(
            "hi",    "Hello! 👋 How can I help you today?",
            "hello", "Hi there! 😊 Welcome to our WhatsApp chatbot!",
            "bye",   "Goodbye! 👋 Have a great day!",
            "help",  "📋 Available commands:\n• Hi - Greeting\n• Bye - Farewell\n• Help - Show this menu",
            "thanks", "You're welcome! 😊",
            "thank you", "You're welcome! Feel free to reach out anytime! 🙌"
    );

    private static final String DEFAULT_REPLY =
            "🤖 Sorry, I didn't understand that. Type 'help' to see available commands.";

    /**
     * Processes an incoming message and returns an appropriate reply.
     *
     * @param incoming the incoming WhatsApp message
     * @return the chatbot's reply wrapped in an OutgoingMessage
     */
    public OutgoingMessage processMessage(IncomingMessage incoming) {
        // ── Log the incoming message ──────────────────────────────────────
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FMT);
        logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        logger.info("📩  INCOMING MESSAGE");
        logger.info("    Timestamp : {}", timestamp);
        logger.info("    From      : {}", incoming.getFrom());
        logger.info("    Name      : {}", incoming.getName() != null ? incoming.getName() : "N/A");
        logger.info("    Message   : {}", incoming.getMessage());
        logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // ── Generate reply ────────────────────────────────────────────────
        String userMessage = incoming.getMessage().trim().toLowerCase();
        String reply = PREDEFINED_REPLIES.getOrDefault(userMessage, DEFAULT_REPLY);

        OutgoingMessage outgoing = new OutgoingMessage(incoming.getFrom(), reply);

        logger.info("📤  OUTGOING REPLY");
        logger.info("    To        : {}", outgoing.getTo());
        logger.info("    Reply     : {}", outgoing.getReply());
        logger.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        return outgoing;
    }
}
