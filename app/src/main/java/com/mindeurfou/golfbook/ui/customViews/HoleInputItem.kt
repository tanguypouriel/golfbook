package com.mindeurfou.golfbook.ui.customViews

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mindeurfou.golfbook.R

class HoleInputItem (
    context: Context,
    holeNumber: Int,
    private val setPar: (par: Int) -> Unit
) : ConstraintLayout(context){

    private val holeEditText: TextInputEditText
    private val holeInputLayout: TextInputLayout

    init {
        val view = inflate(context, R.layout.item_hole_input, this)

        holeEditText = view.findViewById(R.id.holeEditText)
        holeInputLayout = view.findViewById(R.id.holeInputLayout)

        holeInputLayout.hint = context.getString(R.string.holeNumber, holeNumber)

        holeEditText.doOnTextChanged { text, _, _, _ ->
            setPar(text.toString().toInt())
        }
    }
}