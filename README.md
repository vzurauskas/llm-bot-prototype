# LLM Bot Prototype

A pure Java prototype for running open-source LLMs locally using 
[jlama](https://github.com/tjake/Jlama). No external installations required - 
just Java and a model file.

## Prerequisites

- Java 21 or later (required for Vector API performance)
- Maven 3.6+

## Download a Model

Before running the bot, download a model using the included jlama CLI:

```bash
mvn compile exec:java@download-model \
  -Dexec.args="download tjake/Llama-3.2-1B-Instruct-JQ4"
```

This downloads the model to `./models/` in the project directory.

### Alternative: Manual download

Download directly from 
[HuggingFace](https://huggingface.co/tjake/Llama-3.2-1B-Instruct-JQ4/tree/main)
and place all files in a directory like `./models/Llama-3.2-1B-Instruct-JQ4/`.

## Build

```bash
mvn clean compile
```

## Run

The easiest way to run is via the test:

```bash
mvn test -Dtest=ChatTest
```

This loads the model and asks "What is 2+2?", printing the response.

### Interactive Mode (requires JVM setup)

For interactive mode, you need to pass JVM arguments for the Vector API:

```bash
MAVEN_OPTS="--add-modules jdk.incubator.vector --enable-native-access=ALL-UNNAMED" \
  mvn exec:java -Dexec.args="models/tjake_Llama-3.2-1B-Instruct-JQ4"
```

Then interact with the bot:

```
Loading model from: models/tjake_Llama-3.2-1B-Instruct-JQ4
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

1. `Chat` loads a SafeTensors model using jlama's `ModelSupport.loadModel()`
2. `LlmBot` creates a `Chat` instance and runs an interactive loop
3. User input is sent to the model via `chat.send(message)`
4. The model generates a response token-by-token

## Platform Notes

The pom.xml includes `jlama-native` with the `osx-aarch_64` classifier for 
Apple Silicon Macs. For other platforms, change the classifier:

| Platform | Classifier |
|----------|------------|
| macOS ARM (M1/M2/M3) | `osx-aarch_64` |
| macOS Intel | `osx-x86_64` |
| Linux x64 | `linux-x86_64` |
| Windows x64 | `windows-x86_64` |

## Performance Notes

- jlama uses native SIMD operations when the native library is loaded
- Quantized models (Q4, Q5) run faster and use less memory
- First response may be slower due to model warm-up
