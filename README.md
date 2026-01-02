# Java Project Template Initializer

An AI-driven Cursor rule that initializes new Java/Maven projects by copying configuration files and Cursor rules from an existing local template repository.

## Quick Start

1. Create a new empty repository on GitHub
2. Clone it locally
3. Copy `.cursor/rules/000_project_initializer.mdc` into the cloned repo
4. Open the repo in Cursor
5. Say: `init from template /path/to/your/template-repo`
6. Answer the prompts for groupId, artifactId, and package name

## What Gets Copied

The AI agent will scan your template repository and copy:

| File/Directory | Handling |
|----------------|----------|
| `.cursor/rules/*.mdc` | Copied verbatim |
| `config/` (checkstyle, spotless, etc.) | Copied verbatim |
| `.gitignore` | Copied verbatim |
| `.editorconfig` | Copied verbatim |
| `LICENSE` | Copied verbatim |
| `pom.xml` | Templated with your project details |

The agent also creates the standard Maven directory structure:
- `src/main/java/{package}`
- `src/main/resources`
- `src/test/java/{package}`
- `src/test/resources`

## Example

```
User: init from template /Users/me/repos/my-java-template

AI: I'll initialize this project from /Users/me/repos/my-java-template. 
    I need a few details:
    - groupId (e.g., com.example)?
    - artifactId (e.g., my-app)?
    - base package (e.g., com.example.myapp)?

User: com.acme, awesome-service, com.acme.awesome

AI: [Copies files, creates pom.xml, sets up directories]
    Done! Project initialized with:
    - 5 Cursor rules
    - Checkstyle configuration
    - pom.xml configured for com.acme:awesome-service
    - Package structure at src/main/java/com/acme/awesome
```

## Requirements

- Template must be a **local path** (clone remote repos first)
- New project should already be a cloned Git repository
- Cursor IDE with AI agent capabilities

## How It Works

The rule uses efficient terminal commands (`cp -r`) for bulk copying instead of file-by-file operations. Only `pom.xml` is read and modified to substitute your project details. Each phase verifies success before proceeding.

