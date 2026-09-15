# Send Code Reference to Terminal

这是一个 IntelliJ IDEA 插件，可将当前编辑器中的代码选区格式化为类似 Cursor 风格的代码引用（例如 `@path/to/File.kt:10-18`），并直接发送到 IDEA 内置终端的当前会话中。同时支持在编辑器与终端之间进行便捷的双向焦点切换。

## 功能特性

### 1. 发送代码引用至终端 (Send Code Reference to Terminal)
- **选区引用**：选中的多行代码会生成相对项目的引用格式，例如 `@src/main/App.kt:10-18`。
- **单行引用**：未选中代码时，默认使用当前光标所在的单行，例如 `@src/main/App.kt:10`。
- **安全输入**：引用文本末尾不附加换行符，不会在终端中被立即回车执行，方便继续输入提示词或命令。
- **自动切换焦点**：发送代码引用后，光标焦点会自动切换至终端输入区域。
- **终端兼容**：优先适配 IntelliJ IDEA 2025.3+ 全新的重构终端（Reworked Terminal），同时自动降级兼容经典终端（Classic Terminal）。
- **触发方式**：
  - 在编辑器内右键，选择 **Send Code Reference to Terminal**。
  - 可在 **Settings / Preferences | Keymap** 中搜索 `Send Code Reference to Terminal` 自行绑定快捷键。

### 2. 焦点双向切换 (Toggle Terminal/Editor Focus)
- **焦点切换**：
  - 当前焦点在编辑器时，切换至终端；
  - 当前焦点在终端时，切换回当前编辑器的代码区域。
- **右键菜单集成**：已添加到编辑器右键菜单（`EditorPopupMenu`）及终端各区域的右键菜单（`Terminal.OutputContextMenu`、`Terminal.PromptContextMenu` 等），菜单文案会根据当前所在区域自适应显示为 **Switch Focus to Terminal** 或 **Switch Focus to Editor**。
- **自定义快捷键**：未配置默认快捷键（避免键位冲突），可在 **Settings / Preferences | Keymap** 中搜索 `Toggle Terminal/Editor Focus` 自由绑定快捷键。

## 本地开发与构建

本项目使用 [mise](https://mise.jdx.dev/) 锁定 Java 21 与 Gradle 工具链环境。

```shell
# 安装依赖工具链
mise install

# 运行测试
mise exec -- gradle test

# 校验插件项目配置
mise exec -- gradle verifyPluginProjectConfiguration

# 构建打包插件
mise exec -- gradle buildPlugin

# 启动沙箱 IDE 调试
mise exec -- gradle runIde
```

打包生成的插件 ZIP 文件位于 `build/distributions/` 目录下。

## 安装方式

1. 运行 `mise exec -- gradle buildPlugin` 生成插件安装包；
2. 打开 IntelliJ IDEA，进入 **Settings (Preferences) -> Plugins**；
3. 点击右上角齿轮图标 ⚙️，选择 **Install Plugin from Disk...**；
4. 选择 `build/distributions/send-code-ref-to-terminal-0.1.0.zip`；
5. 重启 IDEA 即可生效。
