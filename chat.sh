#!/bin/bash

URL="http://localhost:8282/webhook"

GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'
BOLD='\033[1m'

clear
echo -e "${BOLD}========================================${NC}"
echo -e "${BOLD}   WhatsApp Chatbot CLI Client${NC}"
echo -e "${BOLD}========================================${NC}"
echo -e "Type your message and press Enter."
echo -e "Type 'exit' to quit."
echo -e "----------------------------------------"

while true; do
    echo -ne "${GREEN}${BOLD}You: ${NC}"
    read msg
    
    if [[ "$msg" == "exit" || "$msg" == "quit" || "$msg" == "bye" ]]; then
        echo -e "${CYAN}${BOLD}Bot:${NC} Goodbye!"
        break
    fi

    if [[ -z "$msg" ]]; then
        continue
    fi

    REPLY=$(curl -s -X POST "$URL" -H "Content-Type: text/plain" -d "$msg")
    
    echo -ne "${CYAN}${BOLD}Bot:${NC} "
    echo "$REPLY"
    echo -e "----------------------------------------"
done
