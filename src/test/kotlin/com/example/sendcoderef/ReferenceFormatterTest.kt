package com.example.sendcoderef

import kotlin.test.Test
import kotlin.test.assertEquals

class ReferenceFormatterTest {
    @Test
    fun formatsSingleLine() {
        assertEquals("@src/App.kt:10", ReferenceFormatter.format("src\\App.kt", 10, 10))
    }

    @Test
    fun formatsInclusiveMultiLineRange() {
        assertEquals("@src/App.kt:10-18", ReferenceFormatter.format("src/App.kt", 10, 18))
    }
}
