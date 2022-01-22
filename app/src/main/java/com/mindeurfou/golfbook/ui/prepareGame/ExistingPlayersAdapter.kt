package com.mindeurfou.golfbook.ui.prepareGame

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.setAvatarResource

class ExistingPlayersViewHolder(
    private val context: Context,
    itemView: View,
) : RecyclerView.ViewHolder(itemView) {

    private val username: TextView = itemView.findViewById(R.id.username)
    private val avatar: ImageView = itemView.findViewById(R.id.imageAvatar)
    var selected: Boolean = false

    fun bind(
        player: Player,
        onClick: () -> Unit
    ) {
        username.text = player.username
        avatar.setAvatarResource(player.avatarId)

        if (selected) {
            avatar.background = ContextCompat.getDrawable(context, R.drawable.circle_shape)
            avatar.backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.colorSecondary))
        } else
            avatar.background = null

        itemView.setOnClickListener { onClick() }
    }
}

class ExistingPlayersAdapter(
    private val context: Context,
    private val players: List<Player>,
    private val onClick: (player: Player) -> Unit
) : RecyclerView.Adapter<ExistingPlayersViewHolder>(){

    private var selectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistingPlayersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_existing_player, parent, false)

        return ExistingPlayersViewHolder(context, view)

    }

    override fun onBindViewHolder(holder: ExistingPlayersViewHolder, position: Int) {
        selectedPosition?.let { holder.selected = it == position }

        holder.bind(players[position]) {

            onClick(players[position])
            selectedPosition?.let { notifyItemChanged(it) }
            selectedPosition = position
            notifyItemChanged(selectedPosition!!)
        }
    }

    override fun getItemCount(): Int = players.size
}