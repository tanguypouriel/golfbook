package com.mindeurfou.golfbook.ui.games

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.ui.common.BaseAdapter
import com.mindeurfou.golfbook.ui.common.BaseViewHolder
import com.mindeurfou.golfbook.utils.setAvatarResource

class GamesViewHolder(itemView: View) : BaseViewHolder<Game>(itemView) {

    private val gameName: TextView = itemView.findViewById(R.id.titleGame)
    private val gameState: TextView = itemView.findViewById(R.id.gameState)
    private val gameCourse: TextView = itemView.findViewById(R.id.courseName)

    private val player1Avatar: ImageView = itemView.findViewById(R.id.imageAvatar1)
    private val player2Avatar: ImageView = itemView.findViewById(R.id.imageAvatar2)
    private val player3Avatar: ImageView = itemView.findViewById(R.id.imageAvatar3)
    private val player4Avatar: ImageView = itemView.findViewById(R.id.imageAvatar4)
    private val player1Name: TextView = itemView.findViewById(R.id.player1Name)
    private val player2Name: TextView = itemView.findViewById(R.id.player2Name)
    private val player3Name: TextView = itemView.findViewById(R.id.player3Name)
    private val player4Name: TextView = itemView.findViewById(R.id.player4Name)

    override fun bind(item: Game, position: Int, onClick: (Game) -> Unit) {

        gameName.text =  item.name
        gameState.text = itemView.context.getString(R.string.bulletPointString, item.state)
        gameCourse.text = itemView.context.getString(R.string.bulletPointString, item.courseName)

        item.players.forEachIndexed { i, player -> setPlayer(i, player) }

        itemView.setOnClickListener { onClick(item) }
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

class GamesAdapter(
    games: List<Game>,
    onClick: (game: Game) -> Unit
) : BaseAdapter<Game>(
    list = games,
    layoutItems = listOf(R.layout.item_game_main, R.layout.item_game_first, R.layout.item_game_second, R.layout.item_game_third, R.layout.item_game_fourth),
    onClick
){
    override fun additionalLayout(view: View) {}

    override fun getViewHolder(itemView: View): BaseViewHolder<Game> =
        GamesViewHolder(itemView)

}