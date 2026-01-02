package com.vzurauskas.prototypes.llmbot;

import org.junit.jupiter.api.Test;

/**
 * Simple test to run the Chat with a local LLM.
 * No assertions - just prints the response for manual verification.
 */
final class ChatTest {

    @Test
    void chatsWithModel() {
        final Chat chat = new Chat("models/tjake_Llama-3.2-1B-Instruct-JQ4");
        final String response = chat.send("What is 2+2? Answer briefly.");
        System.out.println("Response: " + response);
    }
}

