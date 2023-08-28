package com.example.a16_room.ui.listeners

interface OnStudentListener {
    fun OnClick(id: Long, source: ClickSourceStudent)
}

enum class ClickSourceStudent {
    TEXT,
    BUTTON_REMOVE
}