package com.mindeurfou.golfbook.ui.prepareGame

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputLayout
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class AddPlayerDialog(
    private val getPlayersEvent: () -> Unit,
    private val existingPlayers: LiveData<DataState<List<Player>>>,
    private val listener: AddPlayerDialogListener
) : DialogFragment() {

    private lateinit var positiveButton: TextView
    private lateinit var negativeButton: TextView
    private lateinit var newPlayerLayout: ConstraintLayout
    private lateinit var existingPlayerLayout: ConstraintLayout
    private lateinit var recycler: RecyclerView
    private lateinit var existingPlayersProgress: ProgressBar
    lateinit var togglePlayer: MaterialButtonToggleGroup
    lateinit var nameInput: TextInputLayout
    lateinit var lastNameInput: TextInputLayout
    lateinit var usernameInput: TextInputLayout
    lateinit var imageAvatar: ImageView
    lateinit var progressBar: ProgressBar

    private var autoUsername = true

    var selectedPlayer: Player? = null

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
        newPlayerLayout = root.findViewById(R.id.newPlayerLayout)
        existingPlayerLayout = root.findViewById(R.id.existingPlayerLayout)
        negativeButton = root.findViewById(R.id.negativeBtn)
        togglePlayer = root.findViewById(R.id.togglePlayer)
        nameInput = root.findViewById(R.id.nameInput)
        lastNameInput = root.findViewById(R.id.lastNameInput)
        usernameInput = root.findViewById(R.id.usernameInput)
        progressBar = root.findViewById(R.id.progressBar)
        recycler = root.findViewById(R.id.existingPlayersRecycler)
        existingPlayersProgress = root.findViewById(R.id.existingPlayersProgress)

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

        existingPlayers.observe(this) {
            when(it) {
                is DataState.Loading -> {
                    recycler.hide()
                    existingPlayersProgress.show()
                }
                is DataState.Failure -> existingPlayersProgress.hide()
                is DataState.Success -> {
                    existingPlayersProgress.hide()
                    recycler.adapter = ExistingPlayersAdapter(requireContext(), it.data) { player ->
                        selectedPlayer = player
                    }
                    recycler.show()
                }
            }
        }

        togglePlayer.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                if (checkedId == R.id.btnExisting) {
                    getPlayersEvent()
                    newPlayerLayout.hide()
                    existingPlayerLayout.show(400)
                }
                else if (checkedId == R.id.btnNew) {
                    existingPlayerLayout.hide()
                    newPlayerLayout.show(400)
                }
            }
        }

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
