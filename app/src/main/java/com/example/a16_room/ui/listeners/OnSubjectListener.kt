package com.example.a16_room.ui.listeners

interface OnSubjectListener {
    fun OnClick(id: Long, source: ClickSourceSubject)
}

enum class ClickSourceSubject {
    OPTION_VIEW_STUDENTS,
    OPTION_EDIT,
    OPTION_REMOVE
}