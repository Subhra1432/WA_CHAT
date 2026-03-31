package com.chatbot.whatsapp;

import com.chatbot.whatsapp.model.IncomingMessage;
import com.chatbot.whatsapp.model.OutgoingMessage;
import com.chatbot.whatsapp.service.ChatbotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WhatsappChatbotApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChatbotService chatbotService;

    @Test
    void contextLoads() {
        assertThat(chatbotService).isNotNull();
    }

    // ── Service Layer Tests ──────────────────────────────────────────

    @Test
    void testHiReply() {
        IncomingMessage msg = new IncomingMessage("919876543210", "Test", "Hi");
        OutgoingMessage reply = chatbotService.processMessage(msg);
        assertThat(reply.getReply()).contains("Hello");
    }

    @Test
    void testByeReply() {
        IncomingMessage msg = new IncomingMessage("919876543210", "Test", "Bye");
        OutgoingMessage reply = chatbotService.processMessage(msg);
        assertThat(reply.getReply()).contains("Goodbye");
    }

    @Test
    void testHelpReply() {
        IncomingMessage msg = new IncomingMessage("919876543210", "Test", "help");
        OutgoingMessage reply = chatbotService.processMessage(msg);
        assertThat(reply.getReply()).contains("Available commands");
    }

    @Test
    void testCaseInsensitive() {
        IncomingMessage msg = new IncomingMessage("919876543210", "Test", "HI");
        OutgoingMessage reply = chatbotService.processMessage(msg);
        assertThat(reply.getReply()).contains("Hello");
    }

    @Test
    void testUnknownMessageGetsDefaultReply() {
        IncomingMessage msg = new IncomingMessage("919876543210", "Test", "random text");
        OutgoingMessage reply = chatbotService.processMessage(msg);
        assertThat(reply.getReply()).contains("didn't understand");
    }

    @Test
    void testReplyToField() {
        IncomingMessage msg = new IncomingMessage("919876543210", "Test", "Hi");
        OutgoingMessage reply = chatbotService.processMessage(msg);
        assertThat(reply.getTo()).isEqualTo("919876543210");
    }

    // ── Controller / Integration Tests ───────────────────────────────

    @Test
    void testWebhookPostHi() throws Exception {
        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"919876543210\",\"name\":\"Test\",\"message\":\"Hi\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.to").value("919876543210"))
                .andExpect(jsonPath("$.reply").value(org.hamcrest.Matchers.containsString("Hello")));
    }

    @Test
    void testWebhookPostBye() throws Exception {
        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"919876543210\",\"name\":\"Test\",\"message\":\"Bye\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value(org.hamcrest.Matchers.containsString("Goodbye")));
    }

    @Test
    void testWebhookPostValidationError() throws Exception {
        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"\",\"message\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    void testWebhookGetHealthCheck() throws Exception {
        mockMvc.perform(get("/webhook"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("active")));
    }
}
