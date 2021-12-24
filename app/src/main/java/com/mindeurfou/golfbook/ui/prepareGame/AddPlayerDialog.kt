package com.mindeurfou.golfbook.ui.prepareGame

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.ui.MainActivity
import com.mindeurfou.golfbook.utils.setAvatarResource
import kotlin.random.Random

class AddPlayerDialog(
    private val listener: DialogListener
) : DialogFragment() {

    private val images = listOf(
        R.drawable.man_1, R.drawable.man_2, R.drawable.man_3, R.drawable.man_4,
        R.drawable.man_5, R.drawable.man_6, R.drawable.man_7, R.drawable.man_8,
        R.drawable.woman_1, R.drawable.woman_2, R.drawable.woman_3, R.drawable.woman_4,
        R.drawable.woman_5, R.drawable.woman_6, R.drawable.woman_7, R.drawable.woman_8,
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.dialog_add_player, null)

        val imageAvatar: ImageView = root.findViewById(R.id.imageAvatar)
        imageAvatar.setOnClickListener {
            imageAvatar.setAvatarResource(randomAvatarImage())
        }
        imageAvatar.setAvatarResource(randomAvatarImage())

        builder
            .setTitle(R.string.addPlayer)
            .setView(root)
            .setPositiveButton(R.string.confirm) { _, _ ->
                listener.onDialogPositiveClick(this)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                listener.onDialogNegativeClick(this)
            }

        return builder.create()
    }

    private fun randomAvatarImage() : Int {
        val index = Random.nextInt(until = images.size)
        return images[index]
    }
}