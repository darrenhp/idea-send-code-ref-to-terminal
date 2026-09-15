# Send Code Reference to Terminal

An IntelliJ IDEA plugin that sends a Cursor-style reference for the current
editor selection to the selected session in IDEA's built-in Terminal.

## Behavior

- Selected code produces `@path/to/File.kt:10-18`.
- With no selection, the caret's current line is used.
- The reference is written without a newline, so it is not executed
  automatically.
- Sending a code reference automatically switches focus to the Terminal.
- Supports the Reworked Terminal available from IntelliJ IDEA 2025.3, with a
  Classic Terminal fallback.
- Can be triggered from the Editor right-click menu (`Send Code Reference to Terminal`), or mapped to a custom shortcut in **Settings / Preferences | Keymap**.
- **Toggle Terminal/Editor Focus** action:
  - Switches focus to the Terminal when focus is in the Editor, and switches back to the Editor when focus is in the Terminal.
  - Added to right-click context menus in both Editor (`EditorPopupMenu`) and Terminal (`Terminal.OutputContextMenu`, `Terminal.PromptContextMenu`, etc.).
  - Can be mapped to any custom shortcut in **Settings / Preferences | Keymap** under `Toggle Terminal/Editor Focus`.

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
