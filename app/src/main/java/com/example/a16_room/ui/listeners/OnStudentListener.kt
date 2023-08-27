package com.example.a16_room.ui.listeners

interface OnStudentListener {
    fun OnClick(id: Int, source: ClickSourceStudent)
}

enum class ClickSourceStudent {
    TEXT,
    BUTTON_REMOVE
}