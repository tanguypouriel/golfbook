package com.mindeurfou.golfbook.ui.onboardGame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.setAvatarResource

class OnBoardGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val gameName: TextView = itemView.findViewById(R.id.titleGame)
    private val joinGame: TextView = itemView.findViewById(R.id.joinGameBtn)

    private val player1Avatar: ImageView = itemView.findViewById(R.id.imageAvatar1)
    private val player2Avatar: ImageView = itemView.findViewById(R.id.imageAvatar2)
    private val player3Avatar: ImageView = itemView.findViewById(R.id.imageAvatar3)
    private val player4Avatar: ImageView = itemView.findViewById(R.id.imageAvatar4)
    private val player1Name: TextView = itemView.findViewById(R.id.player1Name)
    private val player2Name: TextView = itemView.findViewById(R.id.player2Name)
    private val player3Name: TextView = itemView.findViewById(R.id.player3Name)
    private val player4Name: TextView = itemView.findViewById(R.id.player4Name)

    fun bind(item: Game, onClick: (Game) -> Unit) {

        gameName.text =  item.name
        item.players?.forEachIndexed { i, player -> setPlayer(i, player) }

        joinGame.setOnClickListener { onClick(item) }
    }

    private fun setPlayer(index : Int, player: Player) {
        when (index) {
            0 -> {
                player1Avatar.setAvatarResource(player.avatarId)
                player1Name.text = player.username
            }
            1 -> {
                player2Avatar.setAvatarResource(player.avatarId)
                player2Name.text = player.username
            }
            2 -> {
                player3Avatar.setAvatarResource(player.avatarId)
                player3Name.text = player.username
            }

            3 -> {
                player4Avatar.setAvatarResource(player.avatarId)
                player4Name.text = player.username
            }
        }
    }

}

class OnBoardGameAdapter(
    private val games: List<Game>,
    private val onClick: (Game) -> Unit
) : RecyclerView.Adapter<OnBoardGameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardGameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboard_game, parent, false)
        return OnBoardGameViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnBoardGameViewHolder, position: Int) {
        holder.bind(games[position], onClick)
    }

    override fun getItemCount(): Int = games.size
}