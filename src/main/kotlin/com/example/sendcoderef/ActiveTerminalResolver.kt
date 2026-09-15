package com.example.sendcoderef

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.terminal.frontend.toolwindow.TerminalToolWindowTabsManager
import com.intellij.terminal.frontend.view.TerminalView
import org.jetbrains.plugins.terminal.ShellTerminalWidget
import org.jetbrains.plugins.terminal.TerminalToolWindowManager

object ActiveTerminalResolver {
    fun resolveReworked(project: Project): TerminalView? {
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Terminal") ?: return null
        val content = toolWindow.contentManager.selectedContent ?: return null
        val tabs = TerminalToolWindowTabsManager.getInstance(project).tabs
        return tabs.firstOrNull { it.content == content }?.view
    }

    fun resolveClassic(project: Project): ShellTerminalWidget? {
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Terminal") ?: return null
        val content = toolWindow.contentManager.selectedContent ?: return null
        val widget = TerminalToolWindowManager.getWidgetByContent(content) ?: return null
        return ShellTerminalWidget.asShellJediTermWidget(widget.asNewWidget())
    }
}
