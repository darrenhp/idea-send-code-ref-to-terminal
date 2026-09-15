package com.example.sendcoderef

import com.intellij.openapi.application.ApplicationManager
import com.intellij.terminal.frontend.view.TerminalView
import org.jetbrains.plugins.terminal.ShellTerminalWidget

object TerminalSender {
    fun send(view: TerminalView, text: String) {
        view.createSendTextBuilder()
            .useBracketedPasteMode()
            .send(text)
    }

    fun send(widget: ShellTerminalWidget, text: String) {
        ApplicationManager.getApplication().invokeLater {
            widget.executeWithTtyConnector { connector ->
                connector.write(text)
            }
        }
    }
}
