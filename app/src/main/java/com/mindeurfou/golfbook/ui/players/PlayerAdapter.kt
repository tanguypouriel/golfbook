package com.mindeurfou.golfbook.ui.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.ui.common.CustomEdgeTreatment
import com.mindeurfou.golfbook.utils.cropTopEdge
import com.mindeurfou.golfbook.utils.setAvatarResource

class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)
    private val playerUsername: TextView = itemView.findViewById(R.id.titlePlayer)
    private val playerName: TextView = itemView.findViewById(R.id.playerName)
    private val playerLastName: TextView = itemView.findViewById(R.id.playerLastName)

    fun bind(player: Player, onClick: (player: Player) -> Unit) {
        imageAvatar.setAvatarResource(player.drawableResourceId)
        playerUsername.text = player.username
        playerName.text = player.name
        playerLastName.text = player.lastName
        itemView.setOnClickListener { onClick(player) }
    }
}

class PlayerAdapter(
    private val players: List<Player>,
    private val onClick: (player: Player) -> Unit
) : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        val cardView : MaterialCardView = view.findViewById(R.id.cardView)
        cardView.cropTopEdge()

        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position], onClick)
    }

    override fun getItemCount(): Int = players.size

}