# Send Code Reference to Terminal

An IntelliJ IDEA plugin that sends a Cursor-style reference for the current
editor selection to the selected session in IDEA's built-in Terminal.

## Behavior

- Selected code produces `@path/to/File.kt:10-18`.
- With no selection, the caret's current line is used.
- The reference is written without a newline, so it is not executed
  automatically.
- Supports the Reworked Terminal available from IntelliJ IDEA 2025.3, with a
  Classic Terminal fallback.
- The default shortcut is `Cmd+Shift+G` on macOS and `Ctrl+Shift+G` elsewhere.

## Development

The project uses [mise](https://mise.jdx.dev/) to pin the Java 21 and Gradle
toolchain. Install mise, then run:

```shell
mise install
mise exec -- gradle test
mise exec -- gradle verifyPluginProjectConfiguration
mise exec -- gradle buildPlugin
mise exec -- gradle runIde
```

The generated plugin ZIP is placed under `build/distributions/`.
# idea-send-code-ref-to-terminal
