package com.mindeurfou.golfbook.ui.players

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.utils.CustomEdgeTreatment

class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val imageAvatar: ImageView = itemView.findViewById(R.id.imageAvatar)

}

class PlayerAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<PlayerViewHolder>(){

    private val dummy = listOf("a", "b")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutId = when (viewType) {
            1 -> R.layout.item_player_first
            2 -> R.layout.item_player_second
            3 -> R.layout.item_player_third
            0 -> R.layout.item_player_fourth
            else -> R.layout.item_player_main
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val cardView : MaterialCardView = view.findViewById(R.id.cardView)
        setShape(cardView)
        
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick() }
        if (position == 0) {
            val layoutParams : StaggeredGridLayoutManager.LayoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            holder.itemView.layoutParams = layoutParams
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return 5
        else
            return (position % 4)
    }

    override fun getItemCount(): Int = dummy.size + 7

    @SuppressLint("RestrictedApi")
    private fun setShape(view: MaterialCardView) {
        val builder = ShapeAppearanceModel.Builder()
            .setAllCornerSizes(50f)
            .setTopEdge(CustomEdgeTreatment())
            .build()
        view.shapeAppearanceModel = builder
    }
}