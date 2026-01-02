# LLM Bot Prototype

A pure Java prototype for running open-source LLMs locally using 
[jlama](https://github.com/tjake/Jlama). No external installations required - 
just Java and a model file.

## Prerequisites

- Java 21 or later (required for Vector API performance)
- Maven 3.6+

## Download a Model

Before running the bot, download a GGUF model. You can use jlama's built-in
CLI:

```bash
mvn compile exec:java \
  -Dexec.mainClass="com.github.tjake.jlama.cli.JlamaCli" \
  -Dexec.args="download tjake/Llama-3.2-1B-Instruct-JQ4"
```

This downloads the model to `~/.jlama/models/`.

Alternatively, download models manually from 
[HuggingFace](https://huggingface.co/models?library=gguf).

## Build

```bash
mvn clean compile
```

## Run

```bash
mvn exec:java -Dexec.args="~/.jlama/models/Llama-3.2-1B-Instruct-JQ4"
```

Then interact with the bot:

```
Loading model from: /Users/you/.jlama/models/Llama-3.2-1B-Instruct-JQ4
Model loaded. Type 'exit' to quit.

You: What is the capital of France?
Bot: The capital of France is Paris.

You: exit
Goodbye!
```

## Project Structure

```
src/main/java/com/vzurauskas/prototypes/llmbot/
├── Chat.java      # Encapsulates the LLM model
└── LlmBot.java    # Main entry point with interactive CLI
```

## How It Works

1. `Chat` loads a GGUF model using jlama's `ModelSupport.loadModel()`
2. `LlmBot` creates a `Chat` instance and runs an interactive loop
3. User input is sent to the model via `chat.send(message)`
4. The model generates a response token-by-token

## Performance Notes

- jlama uses Java's Vector API for SIMD acceleration (best on Java 21+)
- Quantized models (Q4, Q5) run faster and use less memory
- First response may be slower due to model warm-up
