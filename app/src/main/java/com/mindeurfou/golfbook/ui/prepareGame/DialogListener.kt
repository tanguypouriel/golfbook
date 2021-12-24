package com.mindeurfou.golfbook.ui.prepareGame

import androidx.fragment.app.DialogFragment

interface DialogListener {
    fun onDialogPositiveClick(dialog: DialogFragment)
    fun onDialogNegativeClick(dialog: DialogFragment)
}