package com.example.a16_room.ui.viewholders

import android.app.AlertDialog
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.databinding.RowSubjectBinding
import com.example.a16_room.ui.listeners.ClickSourceSubject
import com.example.a16_room.ui.listeners.OnSubjectListener

class SubjectViewHolder(
    private val bind: RowSubjectBinding,
    private val listener: OnSubjectListener
) : RecyclerView.ViewHolder(bind.root) {
    fun bind(subject: SubjectModel) {
        bind.textSubject.text = subject.subjectName

        bind.subjectCard.setOnClickListener {
            listener.OnClick(subject.subjectId, ClickSourceSubject.OPTION_VIEW_STUDENTS)
        }
        bind.buttonRemove.setOnClickListener {
            showPopupMenu(it, subject)
        }
    }

    private fun showPopupMenu(anchorView: View, subject: SubjectModel) {
        val popupMenu = PopupMenu(anchorView.context, anchorView)
        popupMenu.inflate(R.menu.popup_menu_options)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.popup_edit -> {
                    listener.OnClick(subject.subjectId, ClickSourceSubject.OPTION_EDIT)
                    true
                }

                R.id.popup_delete -> {
                    AlertDialog.Builder(itemView.context)
                        .setTitle("Remover Turma ${subject.subjectName}")
                        .setMessage("Tem certeza que deseja remover?")
                        .setPositiveButton("Sim") { dialog, which ->
                            listener.OnClick(subject.subjectId, ClickSourceSubject.OPTION_REMOVE)
                        }
                        .setNegativeButton("Não", null)
                        .create()
                        .show()
                    true

                }

                else -> false
            }
        }

        popupMenu.show()
    }
}