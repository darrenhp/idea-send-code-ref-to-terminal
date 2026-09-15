package com.example.sendcoderef

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.openapi.wm.ToolWindowManager
import javax.swing.SwingUtilities

object TerminalFocusHelper {
    fun focusTerminal(project: Project) {
        val toolWindowManager = ToolWindowManager.getInstance(project)
        val toolWindow = toolWindowManager.getToolWindow("Terminal") ?: return

        toolWindow.activate({
            val reworked = ActiveTerminalResolver.resolveReworked(project)
            val classic = ActiveTerminalResolver.resolveClassic(project)
            val target = reworked?.preferredFocusableComponent
                ?: reworked?.component
                ?: classic?.preferredFocusableComponent
                ?: classic?.component
                ?: toolWindow.contentManager.selectedContent?.preferredFocusableComponent
                ?: toolWindow.contentManager.selectedContent?.component
                ?: toolWindow.component
            IdeFocusManager.getInstance(project).requestFocus(target, true)
        }, true)
    }

    fun focusEditor(project: Project) {
        val fileEditorManager = FileEditorManager.getInstance(project)
        val editor = fileEditorManager.selectedTextEditor
        val target = editor?.contentComponent
            ?: fileEditorManager.selectedEditor?.preferredFocusedComponent
        if (target != null) {
            IdeFocusManager.getInstance(project).requestFocus(target, true)
        }
    }

    fun isTerminalFocused(project: Project, event: AnActionEvent): Boolean {
        val toolWindowManager = ToolWindowManager.getInstance(project)
        if (toolWindowManager.activeToolWindowId == "Terminal") {
            return true
        }

        val toolWindow = toolWindowManager.getToolWindow("Terminal") ?: return false
        if (toolWindow.isActive) {
            return true
        }

        val eventToolWindow = event.getData(PlatformDataKeys.TOOL_WINDOW)
        if (eventToolWindow?.id == "Terminal") {
            return true
        }

        val contextComponent = event.getData(PlatformCoreDataKeys.CONTEXT_COMPONENT)
        if (contextComponent != null && SwingUtilities.isDescendingFrom(contextComponent, toolWindow.component)) {
            return true
        }

        val focusOwner = IdeFocusManager.getInstance(project).focusOwner
        if (focusOwner != null && SwingUtilities.isDescendingFrom(focusOwner, toolWindow.component)) {
            return true
        }

        return false
    }
}
