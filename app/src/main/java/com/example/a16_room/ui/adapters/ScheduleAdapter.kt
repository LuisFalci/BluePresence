package com.example.a16_room.ui.adapters

import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.data.models.ScheduleModel

class ScheduleAdapter(private val context: Context, private val data: MutableList<String>) :
        RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    private var scheduleModelList: List<ScheduleModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = data[position]
        holder.bind(day)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.day)
        private val startTimeButton: Button = itemView.findViewById(R.id.button_pick_start_time)
        private val endTimeButton: Button = itemView.findViewById(R.id.button_pick_end_time)
        fun bind(day: String) {
            dayTextView.text = day

            startTimeButton.setOnClickListener {
                showTimePickerDialog(true)
            }

            endTimeButton.setOnClickListener {
                showTimePickerDialog(false)
            }
        }

        private fun showTimePickerDialog(isStartTime: Boolean) {
            val timePicker = TimePickerDialog(
                    context,
                    { _, selectedHour, selectedMinute ->
                        val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                        if (isStartTime) {
                            startTimeButton.setText(selectedTime)
                        } else {
                            endTimeButton.setText(selectedTime)
                        }
                    },
                    0,
                    0,
                    true
            )

            timePicker.show()
        }
    }
}