package com.mindeurfou.golfbook.ui.tournaments

import android.view.View
import android.widget.TextView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.ui.common.BaseAdapter
import com.mindeurfou.golfbook.ui.common.BaseViewHolder

class TournamentViewHolder(itemView: View) : BaseViewHolder<Tournament>(itemView) {

    private val tournamentName: TextView = itemView.findViewById(R.id.titleTournament)

    override fun bind(item: Tournament) {
        tournamentName.text = item.name
    }
}

class TournamentAdapter(
    tournaments: List<Tournament>,
    private val onClick: () -> Unit
) : BaseAdapter<Tournament>(
    list = tournaments,
    layoutItems = listOf(R.layout.item_tournament_main, R.layout.item_tournament_first, R.layout.item_tournament_second, R.layout.item_tournament_third, R.layout.item_tournament_fourth),
) {

    override fun getViewHolder(itemView: View): BaseViewHolder<Tournament> =
        TournamentViewHolder(itemView)

    override fun onItemClick(itemView: View) {
        onClick()
    }

    override fun additionalLayout(view: View) {}

}