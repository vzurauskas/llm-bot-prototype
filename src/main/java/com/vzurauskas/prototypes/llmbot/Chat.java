package com.vzurauskas.prototypes.llmbot;

import com.github.tjake.jlama.model.AbstractModel;
import com.github.tjake.jlama.model.ModelSupport;
import com.github.tjake.jlama.safetensors.DType;
import com.github.tjake.jlama.safetensors.prompt.PromptContext;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * Encapsulates an LLM model for chat interactions.
 * Uses jlama to load and run GGUF models locally.
 */
public final class Chat {

    private final AbstractModel model;

    /**
     * Creates a new Chat instance with the specified model.
     *
     * @param modelPath Path to the directory containing the model files
     */
    public Chat(final String modelPath) {
        this(new File(modelPath));
    }

    /**
     * Creates a new Chat instance with the specified model.
     *
     * @param modelPath Path to the directory containing the model files
     */
    public Chat(final File modelPath) {
        this.model = ModelSupport.loadModel(
            modelPath,
            DType.F32,
            DType.I8
        );
    }

    /**
     * Sends a message to the model and returns the response.
     *
     * @param message The user's message
     * @return The model's response
     */
    public String send(final String message) {
        final PromptContext context = this.model
            .promptSupport()
            .orElseThrow()
            .builder()
            .addUserMessage(message)
            .build();
        final StringBuilder response = new StringBuilder();
        this.model.generate(
            UUID.randomUUID(),
            context,
            0.7f,
            256,
            (token, time) -> response.append(token)
        );
        return response.toString();
    }
}

