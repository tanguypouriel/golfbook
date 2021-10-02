package com.mindeurfou.golfbook.ui.tournaments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.tournament.local.Tournament

class TournamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tournamentName: TextView = itemView.findViewById(R.id.titleTournament)

    fun bind(tournament: Tournament) {
        tournamentName.text = tournament.name
    }
}

class TournamentAdapter(
    private val tournaments: List<Tournament>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<TournamentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val layoutId = when (viewType) {
            1 -> R.layout.item_tournament_first
            2 -> R.layout.item_tournament_second
            3 -> R.layout.item_tournament_third
            0 -> R.layout.item_tournament_fourth
            else -> R.layout.item_tournament_main
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        if (layoutId == R.layout.item_tournament_main) {
            val layoutParams : StaggeredGridLayoutManager.LayoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            view.layoutParams = layoutParams
        }
        return TournamentViewHolder(view)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick() }
        holder.bind(tournaments[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            5
        else
            (position % 4)
    }

    override fun getItemCount(): Int = tournaments.size

}