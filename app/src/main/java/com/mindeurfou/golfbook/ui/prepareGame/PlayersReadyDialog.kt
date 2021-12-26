package com.mindeurfou.golfbook.ui.prepareGame

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.ui.MainActivity

class PlayersReadyDialog(
    private val listener: DialogListener
) : DialogFragment() {

    var progressLinear: LinearProgressIndicator? = null
    var progressCircular: ProgressBar? = null
    var dialogText: TextView? = null
    var positiveButton: TextView? = null
    var negativeButton: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.dialog_players_ready, null)

        dialogText = root.findViewById(R.id.dialogText)
        progressLinear = root.findViewById(R.id.progressLinear)
        progressCircular = root.findViewById(R.id.progressCircular)

        positiveButton = root.findViewById(R.id.positiveBtn)
        negativeButton = root.findViewById(R.id.negativeBtn)

        positiveButton!!.setOnClickListener {
            listener.onDialogPositiveClick(this)
            it.visibility = View.GONE
            negativeButton!!.visibility = View.GONE
        }
        negativeButton!!.setOnClickListener {
            listener.onDialogNegativeClick(this)
            this.dismiss()
        }

        builder
            .setTitle(R.string.gameStarting)
            .setView(root)

        return builder.create()
    }
}