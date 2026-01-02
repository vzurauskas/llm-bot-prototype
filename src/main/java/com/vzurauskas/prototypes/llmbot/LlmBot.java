package com.vzurauskas.prototypes.llmbot;

import java.io.File;
import java.util.Scanner;

/**
 * Interactive CLI for chatting with a local LLM.
 */
public final class LlmBot {

    private LlmBot() {
        // Utility class
    }

    /**
     * Main entry point.
     *
     * @param args Command line arguments. First argument should be the
     *             path to the model directory.
     */
    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: LlmBot <model-path>");
            System.out.println();
            System.out.println("Example:");
            System.out.println(
                "  mvn exec:java -Dexec.args=\"~/.jlama/models/Llama-3.2-1B\""
            );
            return;
        }
        final String modelPath = args[0].replaceFirst(
            "^~",
            System.getProperty("user.home")
        );
        System.out.println("Loading model from: " + modelPath);
        final Chat chat = new Chat(new File(modelPath));
        System.out.println("Model loaded. Type 'exit' to quit.");
        System.out.println();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("You: ");
                final String input = scanner.nextLine().trim();
                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Goodbye!");
                    break;
                }
                if (input.isEmpty()) {
                    continue;
                }
                System.out.print("Bot: ");
                final String response = chat.send(input);
                System.out.println(response);
                System.out.println();
            }
        }
    }
}

