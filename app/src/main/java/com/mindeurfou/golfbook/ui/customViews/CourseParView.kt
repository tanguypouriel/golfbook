package com.mindeurfou.golfbook.ui.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindeurfou.golfbook.R

class CourseParView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr){

    var par: List<Int>
        get() = parState.value
        set(value) {
            parState.value = value
        }

    private val parState = mutableStateOf(listOf<Int>())

    @Composable
    override fun Content() {
        CoursePar(
            par = parState.value
        )
    }
}

@Composable
private fun CoursePar(
    par: List<Int>
) {

    val lighterGrey = colorResource(id = R.color.lighterGrey)

    var parOutSum: Int? = null

    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.colorSecondary)),
        color = colorResource(id = R.color.grey) // Divider color
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            HeaderRow(true)
            if (par.isNotEmpty()){
                val parOut = par.dropLast(9)
                parOutSum = parOut.reduce { acc, i ->  acc + i }
                ParRow(parOut, parOutSum!!, lighterGrey, false)
            }
            HeaderRow(false)
            if (par.isNotEmpty()){
                val parIn = par.drop(9)
                val parInSum = parIn.reduce { acc, i ->  acc + i } + parOutSum!!
                ParRow(parIn, parInSum, lighterGrey, false)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCoursePar() {
    val par = List(18) {
        if (it % 2 == 0)
            3
        else
            4
    }

    CoursePar(par = par)
}
