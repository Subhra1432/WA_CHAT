# 💬 WhatsApp Chatbot Backend Simulation

A simple WhatsApp chatbot backend simulation built with **Java 17** and **Spring Boot 3.4.4**.

## 🚀 Features

- **REST API Webhook** (`POST /webhook`) — Receives simulated WhatsApp messages
- **Predefined Replies** — Keyword-based auto-responses (case-insensitive):
  | Input | Response |
  |-------|----------|
  | `Hi` | Hello! 👋 How can I help you today? |
  | `Hello` | Hi there! 😊 Welcome to our WhatsApp chatbot! |
  | `Bye` | Goodbye! 👋 Have a great day! |
  | `Help` | 📋 Lists available commands |
  | `Thanks` / `Thank you` | You're welcome! 😊 |
  | _anything else_ | 🤖 Default "type help" response |
- **Message Logging** — Every incoming message is logged with timestamp, sender, and content
- **Input Validation** — Clean error responses for malformed requests
- **Webhook Verification** — `GET /webhook` endpoint mirrors WhatsApp verification flow

## 📁 Project Structure

```
whatsapp-chatbot/
├── pom.xml
├── Dockerfile                        # For Render deployment
├── src/main/java/com/chatbot/whatsapp/
│   ├── WhatsappChatbotApplication.java    # Main entry point
│   ├── controller/
│   │   ├── WebhookController.java         # REST /webhook endpoint
│   │   └── GlobalExceptionHandler.java    # Error handling
│   ├── model/
│   │   ├── IncomingMessage.java           # Request DTO
│   │   └── OutgoingMessage.java           # Response DTO
│   └── service/
│       └── ChatbotService.java            # Chatbot logic & logging
└── src/test/java/com/chatbot/whatsapp/
    └── WhatsappChatbotApplicationTests.java  # Unit + integration tests
```

## 🛠️ Prerequisites

- Java 17+
- Maven 3.8+

## ▶️ Run Locally

```bash
# Clone the repo
git clone https://github.com/<your-username>/whatsapp-chatbot.git
cd whatsapp-chatbot

# Build and run
./mvnw spring-boot:run
```

The server starts at **http://localhost:8080**

## 🧪 Test the API

### Send "Hi"
```bash
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from":"919876543210","name":"Subhrakanta","message":"Hi"}'
```

**Response:**
```json
{
  "to": "919876543210",
  "reply": "Hello! 👋 How can I help you today?"
}
```

### Send "Bye"
```bash
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from":"919876543210","name":"Subhrakanta","message":"Bye"}'
```

**Response:**
```json
{
  "to": "919876543210",
  "reply": "Goodbye! 👋 Have a great day!"
}
```

### Health Check
```bash
curl http://localhost:8080/webhook
```

**Response:**
```
✅ WhatsApp Chatbot Webhook is active!
```

## ✅ Run Tests

```bash
./mvnw test
```

## 🌐 Deploy on Render (Free)

1. Push to GitHub
2. Go to [render.com](https://render.com) → **New Web Service**
3. Connect your GitHub repo
4. Configure:
   - **Build Command:** `./mvnw clean package -DskipTests`
   - **Start Command:** `java -jar target/whatsapp-chatbot-0.0.1-SNAPSHOT.jar`
   - **Environment:** `Docker` (uses the included `Dockerfile`)
5. Deploy! 🎉

## 📦 Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Language |
| Spring Boot 3.4.4 | Framework |
| Spring Web | REST API |
| Jakarta Validation | Input validation |
| Lombok | Boilerplate reduction |
| JUnit 5 + MockMvc | Testing |
| Maven | Build tool |

## 📄 License

MIT License — free to use for assignments and projects.
