package com.mindeurfou.golfbook.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.ScoreSummary

class ScoreSummaryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    var scoreSummary: ScoreSummary? = null
        set(value) {
            rankView.text = value?.rank
            nameView.text = value?.name
            scoreNetView.text = value?.score
            field = value
        }

//    var rank: String
//        get() = rankView.text.toString()
//        set(value) {
//            rankView.text = value
//        }
//
//    var name: String
//        get() = nameView.text.toString()
//        set(value) {
//            nameView.text = value
//        }
//
//    var scoreNet: String
//        get() = scoreNetView.text.toString()
//        set(value) {
//            scoreNetView.text = value
//        }

    private val rankView: TextView
    private val nameView: TextView
    private val scoreNetView: TextView

    init {
        val view = inflate(context, R.layout.item_score_summary, this)

        rankView = view.findViewById(R.id.rank)
        nameView = view.findViewById(R.id.name)
        scoreNetView = view.findViewById(R.id.scoreNet)
    }
}