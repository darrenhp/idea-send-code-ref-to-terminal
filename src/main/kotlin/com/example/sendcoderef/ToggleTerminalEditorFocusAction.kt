package com.example.sendcoderef

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.wm.ToolWindowManager

class ToggleTerminalEditorFocusAction : AnAction(), DumbAware {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.EDT

    override fun update(event: AnActionEvent) {
        val project = event.project
        if (project == null) {
            event.presentation.isEnabledAndVisible = false
            return
        }
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Terminal")
        if (toolWindow == null) {
            event.presentation.isEnabledAndVisible = false
            return
        }

        event.presentation.isEnabledAndVisible = true
        val inTerminal = TerminalFocusHelper.isTerminalFocused(project, event)
        event.presentation.text = if (inTerminal) "Switch Focus to Editor" else "Switch Focus to Terminal"
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        if (TerminalFocusHelper.isTerminalFocused(project, event)) {
            TerminalFocusHelper.focusEditor(project)
        } else {
            TerminalFocusHelper.focusTerminal(project)
        }
    }
}
