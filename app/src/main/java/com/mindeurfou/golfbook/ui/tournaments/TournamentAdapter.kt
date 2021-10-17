package com.mindeurfou.golfbook.ui.tournaments

import android.view.View
import android.widget.TextView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import com.mindeurfou.golfbook.ui.common.BaseAdapter
import com.mindeurfou.golfbook.ui.common.BaseViewHolder

class TournamentViewHolder(itemView: View) : BaseViewHolder<Tournament>(itemView) {

    private val tournamentName: TextView = itemView.findViewById(R.id.titleTournament)
    private val tournamentState: TextView = itemView.findViewById(R.id.tournamentStateText)
    private val tournamentDate: TextView = itemView.findViewById(R.id.tournamentDate)
    private val tournamentCourse: TextView = itemView.findViewById(R.id.tournamentCourse)

    override fun bind(item: Tournament, position: Int, onClick: (Tournament) -> Unit) {
        tournamentName.text = item.name
        tournamentCourse.text = "Parcours du chêne"
        tournamentDate.text = item.createdAt.toString()
        tournamentState.text = item.state.toString()
        itemView.setOnClickListener { onClick(item) }
    }
}

class TournamentAdapter(
    tournaments: List<Tournament>,
    onClick: (tournament: Tournament) -> Unit
) : BaseAdapter<Tournament>(
    list = tournaments,
    layoutItems = listOf(R.layout.item_tournament_main, R.layout.item_tournament_first, R.layout.item_tournament_second, R.layout.item_tournament_third, R.layout.item_tournament_fourth),
    onClick
) {
    override fun getViewHolder(itemView: View): BaseViewHolder<Tournament> =
        TournamentViewHolder(itemView)

    override fun additionalLayout(view: View) {}
}