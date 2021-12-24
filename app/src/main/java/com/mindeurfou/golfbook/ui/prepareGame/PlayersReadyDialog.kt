package com.mindeurfou.golfbook.ui.prepareGame

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.ui.MainActivity

class PlayersReadyDialog(
    private val listener: DialogListener
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.dialog_players_ready, null)

        val dialogText : TextView = root.findViewById(R.id.dialogText)
        dialogText.text = "Joueurs prêts : 1 sur 3"

        builder
            .setTitle(R.string.gameStarting)
            .setView(root)
            .setPositiveButton(R.string.accept) { _, _ ->
                listener.onDialogPositiveClick(this)
            }
            .setNegativeButton(R.string.refuse) { _, _ ->
                listener.onDialogNegativeClick(this)
            }

        return builder.create()
    }
}