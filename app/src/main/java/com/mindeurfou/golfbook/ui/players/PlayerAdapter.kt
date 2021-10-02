package com.mindeurfou.golfbook.ui.players

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.CustomEdgeTreatment

class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)
    private val playerName: TextView = itemView.findViewById(R.id.titlePlayer)

    fun bind(player: Player) {

        if (player.drawableResourceId >= R.drawable.man_1 && player.drawableResourceId <= R.drawable.woman_8)
            imageAvatar.setImageResource(player.drawableResourceId)
        else
            imageAvatar.setImageResource(R.drawable.man_1)

        playerName.text = player.username
    }
}

class PlayerAdapter(
    private val players: List<Player>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<PlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutId = when (viewType) {
            1 -> R.layout.item_player_first
            2 -> R.layout.item_player_second
            3 -> R.layout.item_player_third
            0 -> R.layout.item_player_fourth
            else -> R.layout.item_player_main
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        if (layoutId == R.layout.item_player_main) {
            val layoutParams : StaggeredGridLayoutManager.LayoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            view.layoutParams = layoutParams
        }
        val cardView : MaterialCardView = view.findViewById(R.id.cardView)
        setShape(cardView)
        
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick() }
        holder.bind(players[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            5
        else
            (position % 4)
    }

    override fun getItemCount(): Int = players.size

    @SuppressLint("RestrictedApi")
    private fun setShape(view: MaterialCardView) {
        val builder = ShapeAppearanceModel.Builder()
            .setAllCornerSizes(50f)
            .setTopEdge(CustomEdgeTreatment())
            .build()
        view.shapeAppearanceModel = builder
    }
}