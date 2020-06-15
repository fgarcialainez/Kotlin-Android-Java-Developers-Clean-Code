package com.fgarcialainez.habittrainer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fgarcialainez.habittrainer.db.HabitDbTable
import com.fgarcialainez.habittrainer.model.Habit
import kotlinx.android.synthetic.main.single_card.view.*

class HabitsAdapter(val context: Context) : RecyclerView.Adapter<HabitsAdapter.HabitViewHolder>() {

    private var habits = mutableListOf<Habit>()

    init {
        // Read habits from DB
        readHabits()
    }

    fun refreshData() {
        // Clear previous habits
        habits.clear()

        // Read habits from DB
        readHabits()

        // Update data
        notifyDataSetChanged()
    }

    private fun readHabits() {
        // Read habits from DB
        habits.addAll(HabitDbTable(context).readAllHabits())
    }

    class HabitViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    // Specifies the number of items to show
    override fun getItemCount() = habits.size

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent, false)
        return HabitViewHolder(view)
    }

    // Specifies the contents for the shown habit
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits.get(position)

        holder.card.tv_title.text = habit.title
        holder.card.tv_description.text = habit.description
        holder.card.iv_icon.setImageBitmap(habit.image)
    }
}