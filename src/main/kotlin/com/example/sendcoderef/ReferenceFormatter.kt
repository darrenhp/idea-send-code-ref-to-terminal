package com.example.sendcoderef

object ReferenceFormatter {
    fun format(path: String, startLine: Int, endLine: Int): String {
        require(startLine >= 1) { "startLine must be positive" }
        require(endLine >= startLine) { "endLine must not precede startLine" }
        val normalizedPath = path.replace('\\', '/')
        return if (startLine == endLine) {
            "@$normalizedPath:$startLine"
        } else {
            "@$normalizedPath:$startLine-$endLine"
        }
    }
}
