package com.example.sendcoderef

import com.intellij.openapi.actionSystem.ActionPromoter
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.DataContext

class ToggleTerminalEditorFocusActionPromoter : ActionPromoter {
    override fun promote(actions: List<AnAction>, context: DataContext): List<AnAction> {
        return actions.filterIsInstance<ToggleTerminalEditorFocusAction>()
    }
}
