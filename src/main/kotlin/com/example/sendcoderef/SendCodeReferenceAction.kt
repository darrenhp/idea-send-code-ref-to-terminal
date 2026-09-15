package com.example.sendcoderef

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange

class SendCodeReferenceAction : AnAction(), DumbAware {
    override fun update(event: AnActionEvent) {
        val editor = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR)
        event.presentation.isEnabledAndVisible = editor != null && editor.virtualFile != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val editor = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR) ?: return
        val file = editor.virtualFile ?: return
        val document = editor.document
        val range = selectedOrCurrentLine(editor)
        val startLine = document.getLineNumber(range.startOffset) + 1
        val endLine = document.getLineNumber((range.endOffset - 1).coerceAtLeast(range.startOffset)) + 1
        val basePath = project.basePath
        val path = if (basePath != null && file.path.startsWith("$basePath/")) {
            file.path.removePrefix("$basePath/")
        } else {
            file.path
        }
        val reference = ReferenceFormatter.format(path, startLine, endLine)
        val reworkedTerminal = ActiveTerminalResolver.resolveReworked(project)
        if (reworkedTerminal != null) {
            TerminalSender.send(reworkedTerminal, reference)
            TerminalFocusHelper.focusTerminal(project)
            notify(project, "Sent $reference to Terminal", NotificationType.INFORMATION)
            return
        }
        val classicTerminal = ActiveTerminalResolver.resolveClassic(project)
        if (classicTerminal == null) {
            notify(project, "No active IDEA Terminal session was found", NotificationType.WARNING)
            return
        }
        TerminalSender.send(classicTerminal, reference)
        TerminalFocusHelper.focusTerminal(project)
        notify(project, "Sent $reference to Terminal", NotificationType.INFORMATION)
    }

    private fun selectedOrCurrentLine(editor: Editor): TextRange {
        val selection = editor.selectionModel
        if (selection.hasSelection()) {
            return TextRange(selection.selectionStart, selection.selectionEnd)
        }
        val line = editor.document.getLineNumber(editor.caretModel.offset)
        return TextRange(editor.document.getLineStartOffset(line), editor.document.getLineEndOffset(line))
    }

    private fun notify(project: com.intellij.openapi.project.Project, message: String, type: NotificationType) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Send Code Reference")
            .createNotification(message, type)
            .notify(project)
    }
}
