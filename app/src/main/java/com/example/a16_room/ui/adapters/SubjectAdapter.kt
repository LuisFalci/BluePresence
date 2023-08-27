package com.example.a16_room.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.databinding.RowSubjectBinding
import com.example.a16_room.ui.listeners.OnSubjectListener
import com.example.a16_room.ui.viewholders.SubjectViewHolder

class SubjectAdapter : RecyclerView.Adapter<SubjectViewHolder>() {
    private var subjectList: List<SubjectModel> = listOf()
    private lateinit var listener: OnSubjectListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val item = RowSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(subjectList[position])
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    fun updateSubjects(list: List<SubjectModel>) {
        subjectList = list
        notifyDataSetChanged()
    }

    fun attachListener(subjectListener: OnSubjectListener) {
        listener = subjectListener
    }

}