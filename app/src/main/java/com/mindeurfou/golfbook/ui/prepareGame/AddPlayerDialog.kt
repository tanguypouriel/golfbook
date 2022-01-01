package com.mindeurfou.golfbook.ui.prepareGame

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.ui.MainActivity
import com.mindeurfou.golfbook.utils.setAvatarResource
import kotlin.random.Random

class AddPlayerDialog(
    private val listener: AddPlayerDialogListener
) : DialogFragment() {

    private lateinit var positiveButton: TextView
    private lateinit var negativeButton: TextView
    lateinit var nameInput: TextInputLayout
    lateinit var lastNameInput: TextInputLayout
    lateinit var usernameInput: TextInputLayout
    lateinit var imageAvatar: ImageView
    lateinit var progressBar: ProgressBar

    private var autoUsername = true

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

        imageAvatar = root.findViewById(R.id.imageAvatar)
        positiveButton = root.findViewById(R.id.positiveBtn)
        negativeButton = root.findViewById(R.id.negativeBtn)
        nameInput = root.findViewById(R.id.nameInput)
        lastNameInput = root.findViewById(R.id.lastNameInput)
        usernameInput = root.findViewById(R.id.usernameInput)
        progressBar = root.findViewById(R.id.progressBar)

        autoUsername = true

        imageAvatar.setOnClickListener {
            imageAvatar.setAvatarResource(randomAvatarImage())
        }
        imageAvatar.setAvatarResource(randomAvatarImage())

        builder
            .setTitle(R.string.addPlayer)
            .setView(root)

        positiveButton.setOnClickListener {
            listener.onDialogPositiveClick(this)
        }
        negativeButton.setOnClickListener {
            listener.onDialogNegativeClick(this)
        }

        setupAutoUsername()

        return builder.create()
    }

    private fun setupAutoUsername() {
        nameInput.editText?.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                if (autoUsername)
                    usernameInput.editText?.setText(text)

            }
        )

        usernameInput.onFocusChangeListener = View.OnFocusChangeListener { input, hasFocus ->
            if (hasFocus)
                autoUsername = false
        }

    }

    private fun randomAvatarImage() : Int {
        val index = Random.nextInt(until = images.size)
        return images[index]
    }
}

interface AddPlayerDialogListener {
    fun onDialogPositiveClick(dialog: AddPlayerDialog)
    fun onDialogNegativeClick(dialog: AddPlayerDialog)
}
