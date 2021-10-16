package com.mindeurfou.golfbook.ui.players

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.ui.common.BaseAdapter
import com.mindeurfou.golfbook.ui.common.BaseViewHolder
import com.mindeurfou.golfbook.ui.common.CustomEdgeTreatment
import com.mindeurfou.golfbook.utils.setAvatarResource

class PlayerViewHolder(itemView: View) : BaseViewHolder<Player>(itemView) {

    private val imageAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)
    private val playerName: TextView = itemView.findViewById(R.id.titlePlayer)

    override fun bind(item: Player) {

        imageAvatar.setAvatarResource(item.drawableResourceId)
        playerName.text = item.username
    }
}

class PlayerAdapter(
    players: List<Player>,
    private val onClick: (playerId: Int, isSelf: Boolean) -> Unit
) : BaseAdapter<Player>(
    list = players,
    layoutItems = listOf(R.layout.item_player_main, R.layout.item_player_first, R.layout.item_player_second, R.layout.item_player_third, R.layout.item_player_fourth)
) {

    override fun additionalLayout(view: View) {
        val cardView : MaterialCardView = view.findViewById(R.id.cardView)
        val builder = ShapeAppearanceModel.Builder()
            .setAllCornerSizes(50f)
            .setTopEdge(CustomEdgeTreatment())
            .build()
        cardView.shapeAppearanceModel = builder
    }

    override fun onItemClick(itemView: View) {
        onClick(1, true)
    }

    override fun getViewHolder(itemView: View): BaseViewHolder<Player> =
        PlayerViewHolder(itemView)
}