package com.mindeurfou.golfbook.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindeurfou.golfbook.R

class TournamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

class TournamentAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<TournamentViewHolder>(){

    private val dummy = listOf("a", "b")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val layoutId = when (viewType) {
            0 -> R.layout.item_tournament_first
            1 -> R.layout.item_tournament_second
            2 -> R.layout.item_tournament_third
            else -> R.layout.item_tournament_fourth
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return TournamentViewHolder(view)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick() }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 4
    }

    override fun getItemCount(): Int = dummy.size + 4

}